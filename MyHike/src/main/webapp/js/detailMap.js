document.addEventListener('DOMContentLoaded', function () {
    //Get the coordinates which have been written into the data-marker-coordinates field of the map element.
    const mapElement = document.getElementById('map');
    const markerCoordinatesString = mapElement.getAttribute('data-marker-coordinates');
    let markerCoordinates = JSON.parse(markerCoordinatesString)

    //Holds the leaflet map
    let myMap;
    //Array to store markers
    let markers = [];

    //Focus on coordinates of start marker
    myMap = L.map('map').setView([markerCoordinates[0][0],markerCoordinates[0][1]], 15);

    //Copyright of Leaflet
    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        attribution: '© OpenStreetMap contributors'
    }).addTo(myMap);

    //Generate markers from the coordinates and add them to markers array.
    markerCoordinates.forEach((markerCoordinate) => {
        markers.push(L.marker([markerCoordinate[0], markerCoordinate[1]]));
    });

    markers[0].addTo(myMap).set; //Add start marker to map
    markers[markers.length-1].addTo(myMap); //Add finish marker to Map

    setMarkerIcons(markers);
    displayHikingRoute(markers);

    //Sets first marker icon to start-icon, last to finish-icon.
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

        if (markers[0] != null) {
            markers[0].setIcon(startIcon);
        }
        if (markers.length > 1) {
            markers[markers.length-1].setIcon(finishIcon);
        }
    }

    //Displays the hiking route for the given markers. Based on routing by openroute service API.
    function displayHikingRoute(markers) {
        // OpenRouteService API request (url has parameters foot-hiking and geojson, adjusting type of routing and return type)
        let url = "https://api.openrouteservice.org/v2/directions/foot-hiking/geojson";
        let apiKey = "5b3ce3597851110001cf624862d0477273784a3cb757ffdea96c0fff";

        // Rotate the coordinates for openstreet service
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
                    // Extract the route geometry, invert the coordinates since coordinates are handeled in opposite
                    // ways in leaflet and openstreet service.
                    let routeCoordinates = data.features[0].geometry.coordinates;
                    let inverted = [];
                    routeCoordinates.forEach((coordinate) => {
                        inverted.push([coordinate[1], coordinate[0]]);
                    });
                    L.polyline(inverted, {color: 'black'}).addTo(myMap); //Draw route on map.
                })
                .catch(error => {
                    console.error('Error:', error); //TODO improve error handling
                });
        }
    }
});