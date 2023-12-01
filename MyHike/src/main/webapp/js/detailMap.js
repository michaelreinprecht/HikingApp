document.addEventListener('DOMContentLoaded', function () {
    //TODO focus on coordinates from startpoint
    let myMap = L.map('map').setView([49.41461,8.681495], 15);

    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        attribution: '© OpenStreetMap contributors'
    }).addTo(myMap);

    // Array to store markers
    let markers = [
        //TODO fill with markers from db.
        L.marker([49.41461,8.681495]).addTo(myMap),
        L.marker([49.41943,8.686507]).addTo(myMap),
        L.marker([49.420318,8.687872]).addTo(myMap)
    ];

    setMarkerIcons(markers);
    displayHikingRoute(markers);

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
                    L.polyline(inverted, {color: 'black'}).addTo(myMap);
                })
                .catch(error => {
                    console.error('Error:', error);
                });
        }
    }
});