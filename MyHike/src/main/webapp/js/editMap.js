document.addEventListener('DOMContentLoaded', function () {
    //Get the coordinates which have been written into the data-marker-coordinates field of the map element.
    const mapElement = document.getElementById('map');
    const markerCoordinatesString = mapElement.getAttribute('data-marker-coordinates');
    let markerCoordinates = [];
    if (markerCoordinatesString !== null && markerCoordinatesString !== "") {
        markerCoordinates = JSON.parse(markerCoordinatesString);
    }

    //This inputs value will be sent to the database.
    const markerCoordinatesInput = document.getElementById("marker-coordinates");

    //Holds the leaflet map
    let myMap;
    //Array to store markers
    let markers = [];
    //Holds the last drawn hiking route.
    let route;

    if (markerCoordinates.length > 0) {
        //Focus on coordinates of start marker if there are coordinates -> edit page
        myMap = L.map('map').setView([markerCoordinates[0][0], markerCoordinates[0][1]], 15);
    } else {
        //Start coordinates are focused on Vorarlberg if there are no coordinates -> create page
        myMap = L.map('map').setView([47.21329, 9.95118], 9);
        //Attempt to use the Geolocation API to center the map on the users location
        myMap.locate({setView: true, maxZoom: 16});
    }

    //Add a tile layer from OpenStreetMap
    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        attribution: '© OpenStreetMap contributors'
    }).addTo(myMap);

    updateMarkerUsingCoordinates();
    displayHikingRoute(markers);
    updateMarkers(markers);

    //Handle locationfound event. If user allows us to, we will set the location to his current location.
    function onLocationFound(e) {
        // Icon which will display the users current location
        let yourLocationIcon = L.icon({
            iconUrl: 'images/your-location-icon.png',
            iconSize: [50, 50],
            iconAnchor: [25, 50],
            popupAnchor: [0, -50]
        });

        L.marker(e.latlng).setIcon(yourLocationIcon).addTo(myMap).bindPopup("You are here!");
    }

    //Listen for the location found event
    myMap.on('locationfound', onLocationFound);

    //Add a marker and redo routing (and marker icons) if marker is added or dragged
    function onMapClick(e) {
        // Add a marker at the clicked location with the popup
        let marker = L.marker(e.latlng, { draggable: true }).addTo(myMap).on('click', onMarkerClick);

        // Store the marker and coordinates in their respective array
        markers.push(marker);
        markerCoordinates.push([e.latlng.lat, e.latlng.lng]);

        displayHikingRoute(markers);
        updateMarkers(markers);

        // Listen for dragend event to update route when the marker is dragged
        marker.on('dragend', function () {
            displayHikingRoute(markers);
        });
    }

    // Listen for the 'click' event for general map clicks
    myMap.on('click', onMapClick);

    // Define a function to handle marker click event
    function onMarkerClick(e) {
        // Remove the clicked marker from the map
        myMap.removeLayer(e.target);
        // Remove the marker from the array
        markers = markers.filter(marker => marker !== e.target);

        displayHikingRoute(markers);
        updateMarkers(markers);
    }

    // Listen for the 'click' event for marker clicks
    myMap.on('click', onMarkerClick);

    //Sets first marker icon to start-icon, last to finish-icon and all other to waypoint-icon.
    function updateMarkers(markers) {
        let startIcon = L.icon({
            iconUrl: 'images/start-icon.png',
            iconSize: [32, 32],
            iconAnchor: [16, 32],
            popupAnchor: [0, -32]
        });
        let finishIcon = L.icon({
            iconUrl: 'images/finish-icon.png',
            iconSize: [32, 32],
            iconAnchor: [16, 32],
            popupAnchor: [0, -32]
        });
        let waypointIcon = L.icon({
            iconUrl: 'images/waypoint-icon.png',
            iconSize: [32, 32],
            iconAnchor: [16, 32],
            popupAnchor: [0, -32]
        });

        markers.forEach((x) => x.addTo(myMap).setIcon(waypointIcon));
        if (markers[0] != null) {
            markers[0].setIcon(startIcon);
        }
        if (markers.length > 1) {
            markers[markers.length-1].setIcon(finishIcon);
        }
    }

    //Displays the hiking route for the given markers. Based on routing by openroute service API.
    function displayHikingRoute(markers) {
        // OpenRouteService API request
        let url = "https://api.openrouteservice.org/v2/directions/foot-hiking/geojson";
        let apiKey = "5b3ce3597851110001cf624862d0477273784a3cb757ffdea96c0fff";


        let coordinatesLngLat = [];
        markers.forEach((marker) => {
            coordinatesLngLat.push([marker._latlng.lng, marker._latlng.lat]);
        });

        let body = {
            coordinates: coordinatesLngLat,
            continue_straight: true
        };

        if (coordinatesLngLat.length >= 2) {
            fetch(url, {
                method: 'POST',
                headers: {
                    'Accept': 'application/json, application/geo+json, application/gpx+xml, img/png; charset=utf-8',
                    'Content-Type': 'application/json',
                    'Authorization': apiKey,
                },
                body: JSON.stringify(body)
            })
                .then(response => response.json())
                .then(data => {
                    // Extract the route geometry
                    let routeCoordinates = data.features[0].geometry.coordinates;
                    let inverted = [];
                    routeCoordinates.forEach((coordinate) => {
                        inverted.push([coordinate[1], coordinate[0]]);
                    });

                    // Display the route on the map
                    if (route != null) {
                        myMap.removeLayer(route);
                    }
                    route = L.polyline(inverted, {color: 'black'}).addTo(myMap);

                    updateCoordinatesUsingMarker();
                    updateFormValues();
                })
                .catch(error => {
                    console.error('Error:', error);
                });
        }
    }

    function updateMarkerUsingCoordinates() {
        //Generate markers from the coordinates and add them to markers array.
        markerCoordinates.forEach((markerCoordinate) => {
            markers.push(L.marker([markerCoordinate[0], markerCoordinate[1]]));
        });
    }

    function updateCoordinatesUsingMarker() {
        markerCoordinates = [];
        markers.forEach((marker) => {
            markerCoordinates.push([marker._latlng.lat, marker._latlng.lng]);
        });
    }

    //Updates the values of the inputs in the form that will be sent to database.
    function updateFormValues() {
        markerCoordinatesInput.value = JSON.stringify(markerCoordinates);
    }
});