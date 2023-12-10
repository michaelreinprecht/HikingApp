document.addEventListener('DOMContentLoaded', function () {
    //Get the coordinates which have been written into the data-marker-coordinates field of the map element.
    const mapElement = document.getElementById('map');
    let routeCoordinatesJsonString = mapElement.getAttribute('data-route-coordinates');
    let routeCoordinates = JSON.parse(routeCoordinatesJsonString);

    //Holds the leaflet map
    let myMap;

    //Focus on coordinates of start marker
    myMap = L.map('map').setView([routeCoordinates[0][0],routeCoordinates[0][1]], 15);

    //Copyright of Leaflet
    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        attribution: '© OpenStreetMap contributors'
    }).addTo(myMap);

    L.polyline(routeCoordinates, {color: 'black'}).addTo(myMap); //Draw route on map.
    updateMarkers();

    //Sets first marker icon to start-icon, last to finish-icon.
    function updateMarkers() {
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

        //Display start and finish marker with according icon.
        let startMarker = L.marker([routeCoordinates[0][0], routeCoordinates[0][1]]);
        startMarker.addTo(myMap);
        startMarker.setIcon(startIcon);
        let finishMarker = L.marker([routeCoordinates[routeCoordinates.length-1][0], routeCoordinates[routeCoordinates.length-1][1]]);
        finishMarker.addTo(myMap);
        finishMarker.setIcon(finishIcon);

        // Calculate and display the distance and duration
        const routeControl = L.Routing.control({
            waypoints: [
                L.latLng(routeCoordinates[0][0], routeCoordinates[0][1]),
                L.latLng(routeCoordinates[routeCoordinates.length - 1][0], routeCoordinates[routeCoordinates.length - 1][1])
            ],
            routeWhileDragging: true,
            // Include the 'foot' profile for walking
            profile: 'foot',
            show: false // Don't display the route on the map, only calculate the distance and duration
        });

        routeControl.on('routesfound', function (e) {
            const routes = e.routes;
            const distance = routes[0].summary.totalDistance / 1000; // Convert meters to kilometers
            const durationInSeconds = routes[0].summary.totalTime;

            // Convert duration to hours and minutes
            const hours = Math.floor(durationInSeconds / 3600);
            const minutes = Math.floor((durationInSeconds % 3600) / 60);

            // Display the distance and duration on your JSP site in the format 6:30 h
            document.getElementById('distance-container').innerHTML = 'Distance: ' + distance.toFixed(2) + ' km';
            document.getElementById('duration-container').innerHTML = 'Duration: ' + hours + 'h ' + minutes + 'm';
        });

        routeControl.addTo(myMap);
        routeControl.route();
    }
});