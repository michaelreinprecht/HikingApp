//JavaScript for the create.jsp and edit.jsp page

//Displays the image uploaded in the create.jsp or edit.jsp page right after it has been selected.
function displayImage() {
    let input = document.getElementById('fileToUpload');
    let image = document.getElementById('uploadedImage');

    let file = input.files[0];

    if (file) {
        let reader = new FileReader();

        reader.onload = function(e) {
            image.src = e.target.result;
        };

        reader.readAsDataURL(file);
    }
}

//Validates the information entered into the create.jsp or edit.jsp pages form
function validateForm() {
    //Pattern matching for lat and lon coordinates, checks if lat and lon are in a certain range (lat -90 to 90, lon
    // -180 to 180), allows a maximum of 6 decimal points
    const LatPattern = /^[-+]?([1-8]?\d(\.\d{1,6})?|90(\.0{1,6})?)$/;
    const LonPattern = /^[-+]?(180(\.0{1,6})?|((1[0-7]\d|\d{1,2})(\.\d{1,6})?))$/;

    //Start and end coordinates of the hike.
    const startLat = document.getElementById("startLat");
    const startLon = document.getElementById("startLon");
    const endLat = document.getElementById("endLat");
    const endLon = document.getElementById("endLon");

    //Pattern matching for altitude, needs to be at least one number from 1-9 followed by multiple numbers of 0-9
    const altitudePattern = /^[1-9][0-9]*$/;
    const altitude = document.getElementById("altitude");

    //Pattern matching for distance, needs to be at least one number from 0-9, CAN OPTIONALLY be followed by a dot (.)
    //and 2 decimals (lowest possible is 0.00km).
    const distancePattern = /^[0-9]+(?:.[0-9]?[0-9])?$/;
    const distance = document.getElementById("distance");

    const radioStrength = document.querySelector('input[name="strength-rating"]:checked');
    const radioStamina = document.querySelector('input[name="stamina-rating"]:checked');
    const radioDifficulty = document.querySelector('input[name="difficulty-rating"]:checked');
    const radioLandscape = document.querySelector('input[name="landscape-rating"]:checked');

    const checkboxes = document.getElementsByClassName("form-check-input");

    //Get the first selected file (we can only select one file)
    const imageInput = document.getElementById('fileToUpload');
    let image = imageInput.files[0];

    const validationAlert = document.getElementById("validationAlert");

    //This sets the alert to be displayed, if none of the below checks are triggered it will be set to none again and
    //therefore be invisible.
    validationAlert.style.display = "block";

    let monthIsChecked = false;
    //Check if at least one month is checked
    for (let i = 0; i < checkboxes.length; i++) {
        if (checkboxes[i].checked) {
            monthIsChecked = true;
        }
    }
    if (!monthIsChecked) {
        validationAlert.innerHTML = "Please pick at least one recommended month.";
        return false;
    }
    if (!LonPattern.test(startLon.value)){
        validationAlert.innerHTML = "Please enter a valid starting lon-coordinate (ranges from -180.00000 to 180.000000).";
        return false;
    }
    if (!LatPattern.test(startLat.value)){
        validationAlert.innerHTML = "Please enter a valid starting lat-coordinate (ranges from -90.00000 to 90.000000).";
        return false;
    }
    if (!LonPattern.test(endLon.value)){
        validationAlert.innerHTML = "Please enter a valid starting lon-coordinate (ranges from -180.00000 to 180.000000).";
        return false;
    }
    if (!LatPattern.test(endLat.value)){
        validationAlert.innerHTML = "Please enter a valid ending lat-coordinate (ranges from -90.00000 to 90.000000).";
        return false;
    }

    //Altitude/Distance can be null or empty, if they are not they have to match their respective patterns.
    if (altitude.value != null && altitude.value !== "" && !altitudePattern.test(altitude.value)){
        validationAlert.innerHTML = "Please enter a valid altitude in meters.";
        return false;
    }
    if (distance.value != null && distance.value !== "" && !distancePattern.test(distance.value)){
        validationAlert.innerHTML = "Please enter a valid distance in kilometers.";
        return false;
    }

    if (!radioLandscape) {
        validationAlert.innerHTML = "Please rate the landscape.";
        return false;
    }
    if (!radioStrength) {
        validationAlert.innerHTML = "Please rate the required strength for this hike.";
        return false;
    }
    if (!radioStamina) {
        validationAlert.innerHTML = "Please rate the required stamina for this hike.";
        return false;
    }
    if (!radioDifficulty) {
        validationAlert.innerHTML = "Please rate this hikes difficulty.";
        return false;
    }

    //Check if image is either .png, .jpg or .jpeg
    if (document.referrer.includes("create.js")) {
        //If called from create.jsp the image must not be null -> this should cause an alert.
        if (image == null || (!image.name.toLowerCase().endsWith(".png")  && !image.name.toLowerCase().endsWith(".jpg")  && !image.name.toLowerCase().endsWith(".jpeg"))) {
            validationAlert.innerHTML = "Please upload a valid image of type png or jpg.";
            return false;
        }
    } else if (document.referrer.includes("edit.js")) {
        //If called from edit.jsp the image is allowed to be null (since the old image can be used instead)
        if (image != null) {
            if ((!image.name.toLowerCase().endsWith(".png") && !image.name.toLowerCase().endsWith(".jpg") && !image.name.toLowerCase().endsWith(".jpeg"))) {
                validationAlert.innerHTML = "Please upload a valid image of type png or jpg.";
                return false;
            }
        }
    }

    validationAlert.style.display = "none";
    return true;
}

/*
function addPOI() {
    // Collect data from POI inputs
    var poiTitle = document.getElementById("poiTitle").value;
    var poiDescription = document.getElementById("poiDescription").value;
    var poiLon = document.getElementById("poiLon").value;
    var poiLan = document.getElementById("poiLan").value;

    // Get the file input element
    var fileInput = document.getElementById("poiImage");
    // Get the selected file
    var file = fileInput.files[0];

    // Create a FormData object to store both POI data and file data
    var formData = new FormData();

    // Add POI data to FormData
    formData.append("poiTitle", poiTitle);
    formData.append("poiDescription", poiDescription);
    formData.append("poiLon", poiLon);
    formData.append("poiLan", poiLan);

    // Add file data to FormData
    formData.append("fileToUpload", file);

    let poiData = document.getElementById("poiData").value;
    console.log(poiData);
    // Retrieve existing POI data or initialize an empty array
    let existingPoiData;
    if (poiData === "" ) {
        existingPoiData = [];
    } else {
        existingPoiData = JSON.parse(poiData) || [];
    }


    // Add the new POI to the array
    existingPoiData.push({
        title: poiTitle,
        description: poiDescription,
        lon: poiLon,
        lan: poiLan,
        // Add file data if needed
        file: file
    });

    // Update the hidden input field with the updated POI data
    document.getElementById("poiData").value = JSON.stringify(existingPoiData);

    // Update the unordered list display (assuming you have a function for this)
    //displayPoiList(existingPoiData);
}

function displayPoiList(poiData) {
    // Display the POI data in an unordered list
    // You can implement this function based on how you want to display the data
    // For example, create <li> elements for each POI and append them to a <ul>
}*/
