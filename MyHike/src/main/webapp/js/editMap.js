document.addEventListener('DOMContentLoaded', function () {

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

    //Get the coordinates which have been written into the data-marker-coordinates field of the map element.
    const mapElement = document.getElementById('map');
    const routeCoordinatesString = mapElement.getAttribute('data-route-coordinates');
    let routeCoordinates = [];
    if (routeCoordinatesString !== null && routeCoordinatesString !== "") {
        routeCoordinates = JSON.parse(routeCoordinatesString);
    }

    //This inputs value will be sent to the database.
    const routeCoordinatesInput = document.getElementById("route-coordinates");

    //Holds the leaflet map
    let myMap;
    //Array to store markers
    let markers = [];
    //Holds the last drawn hiking route.
    let route;

    if (routeCoordinates.length > 1) {
        //Focus on coordinates of start marker if there are coordinates -> edit page
        myMap = L.map('map').setView([routeCoordinates[0][0], routeCoordinates[0][1]], 15);
        route = L.polyline(routeCoordinates, {color: 'black'}).addTo(myMap);

        //Add start and finish markers for first and last coordinate point.
        let startMarker = L.marker([routeCoordinates[0][0], routeCoordinates[0][1]]);
        let finishMarker = L.marker([routeCoordinates[routeCoordinates.length-1][0], routeCoordinates[routeCoordinates.length-1][1]]);
        markers.push(startMarker);
        markers.push(finishMarker);
        updateFormValues();
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
        let marker = L.marker(e.latlng, { draggable: true }).addTo(myMap);

        // Store the marker and coordinates in their respective array
        markers.push(marker);

        updateMap(markers);
    }

    // Listen for the 'click' event for general map clicks
    myMap.on('click', onMapClick);

    // Define a function to handle marker click event
    function onMarkerClick(e) {
        // Remove the clicked marker from the map
        myMap.removeLayer(e.target);
        // Remove the marker from the array
        markers = markers.filter(marker => marker !== e.target);

        updateMap(markers);
    }

    // Listen for the 'click' event for marker clicks
    myMap.on('click', onMarkerClick);

    function onMarkerDragged() {
        displayHikingRoute(markers);
    }

    //Updates the Map with a new route and new markers, based on the given markers array.
    function updateMap(markers) {
        displayHikingRoute(markers);
        updateMarkers(markers);
    }

    //Sets first marker icon to start-icon, last to finish-icon and all other to waypoint-icon.
    function updateMarkers(markers) {
        let waypointIcon = L.icon({
            iconUrl: 'images/waypoint-icon.png',
            iconSize: [32, 32],
            iconAnchor: [16, 32],
            popupAnchor: [0, -32]
        });

        markers.forEach((marker) => {
            marker.addTo(myMap).setIcon(waypointIcon); //Set all markers to waypoint icon.
            marker.on('click', onMarkerClick); //Listen vor 'click' event on marker.
            marker.on('dragend', onMarkerDragged); //Listen vor 'dragend' event on marker.
            marker.dragging.enable(); //Enable dragging the marker -> default is disabled.
        });
        if (markers[0] != null) {
            markers[0].setIcon(startIcon); //Set Icon for start marker
        }
        if (markers.length > 1) {
            markers[markers.length-1].setIcon(finishIcon); //Set Icon for finish marker
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
            elevation: true,
            continue_straight: true,
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
                    // Getting Distance,Duration, Altitude from API and write them into inputs
                    let coordinatesInverted = data.features[0].geometry.coordinates;
                    let distance = data.features[0].properties.summary.distance;
                    let duration = data.features[0].properties.summary.duration;
                    let altitude = data.features[0].properties.ascent;
                    document.getElementById('distance').value = formatMetersToKilometers(distance);
                    document.getElementById('duration').value = formatSecondsToTime(duration);
                    document.getElementById('altitude').value = altitude.toFixed(0);



                    routeCoordinates = [];
                    coordinatesInverted.forEach((coordinate) => {
                        routeCoordinates.push([coordinate[1], coordinate[0]]);
                    });

                    // Display the route on the map
                    if (route != null) {
                        myMap.removeLayer(route);
                    }
                    route = L.polyline(routeCoordinates, {color: 'black'}).addTo(myMap);

                    updateFormValues();
                })
                .catch(error => {
                    console.error('Error:', error);
                });
        } else {
            //Empty the routeCoordinatesInput if there are not at least 2 valid points selected.
            routeCoordinatesInput.value = "";
        }
    }

    //Updates the values of the inputs in the form that will be sent to database.
    function updateFormValues() {
        routeCoordinatesInput.value = JSON.stringify(routeCoordinates);
    }

    function formatSecondsToTime(seconds) {
        const hours = Math.floor(seconds / 3600);
        const minutes = Math.floor((seconds % 3600) / 60);
        const remainingSeconds = seconds % 60;


        const formattedTime = `${String(hours).padStart(2, '0')}:${String(minutes).padStart(2, '0')}`;

        return formattedTime;
    }

    function formatMetersToKilometers(meters) {
        const kilometers = meters / 1000;
        const formattedKilometers = kilometers.toFixed(2).replace(',', '.');

        return formattedKilometers;
    }
});