document.addEventListener('DOMContentLoaded', function () {
    // Create a new Leaflet map object
    //Start coordinates are focused on Vorarlberg
    let myMap = L.map('map').setView([47.21329, 9.95118], 9);
    // Add a tile layer from OpenStreetMap
    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        attribution: '© OpenStreetMap contributors'
    }).addTo(myMap);

    // Use the Geolocation API to center the map on the user's location
    myMap.locate({setView: true, maxZoom: 16});

    // Array to store markers
    let markers = [];

    //Holds the last drawn hiking route.
    let route;

    let yourLocationIcon = L.icon({
        iconUrl: 'images/your-location-icon.png',
        iconSize: [50, 50],
        iconAnchor: [25, 50],
        popupAnchor: [0, -50]
    });

    // Define a function to handle location found event
    function onLocationFound(e) {
        L.marker(e.latlng).setIcon(yourLocationIcon).addTo(myMap).bindPopup("You are here!");
    }

    // Listen for the location found event
    // If user allows us to, we will set the location to his current location.
    myMap.on('locationfound', onLocationFound);

    function onMapClick(e) {
        // Add a marker at the clicked location with the popup
        let marker = L.marker(e.latlng, { draggable: true }).addTo(myMap).on('click', onMarkerClick);

        // Store the marker in the array
        markers.push(marker);
        displayHikingRoute(markers);
        setMarkerIcons(markers);

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
        setMarkerIcons(markers);
    }

    // Listen for the 'click' event for marker clicks
    myMap.on('click', onMarkerClick);

    //Sets first marker icon to start-icon, last to finish-icon and all other to waypoint-icon.
    function setMarkerIcons(markers) {
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

        markers.forEach((x) => x.setIcon(waypointIcon));
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


        let coordinates = [];
        markers.forEach((marker) => {
            coordinates.push([marker._latlng.lng, marker._latlng.lat]);
        });

        let body = {
            coordinates: coordinates,
            continue_straight: true
        };

        if (coordinates.length >= 2) {
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
                    console.log('Response:', data);

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
                })
                .catch(error => {
                    console.error('Error:', error);
                });
        }
    }
});