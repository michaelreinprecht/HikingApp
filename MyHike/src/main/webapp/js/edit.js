//JavaScript for the edit.js page

//Displays the image uploaded in the edit.jsp Page right after it has been selected.
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

//Validates the information entered into the edit.jsp pages form
function validateForm() {
    //Pattern matching for lat and lon coordinates, checks if lat and lon are in a certain range, allows a maximum of 6 decimal points
    const LatPattern = /^[-+]?([1-8]?\d(\.\d{1,6})?|90(\.0{1,6})?)$/;
    const LonPattern = /^[-+]?(180(\.0{1,6})?|((1[0-7]\d|\d{1,2})(\.\d{1,6})?))$/;

    //Start and end coordinates of the hike.
    const startLat = document.getElementById("startLat");
    const startLon = document.getElementById("startLon");
    const endLat = document.getElementById("endLat");
    const endLon = document.getElementById("endLon");

    const altitudePattern = /^[1-9][0-9]*$/;
    const altitude = document.getElementById("altitude");

    const distancePattern = /^[0-9]+(?:.[0-9]?[0-9])?$/;
    const distance = document.getElementById("distance");

    const radioStrength = document.querySelector('input[name="strength-rating"]:checked');
    const radioStamina = document.querySelector('input[name="stamina-rating"]:checked');
    const radioDifficulty = document.querySelector('input[name="difficulty-rating"]:checked');
    const radioLandscape = document.querySelector('input[name="landscape-rating"]:checked');

    const checkboxes = document.getElementsByClassName("form-check-input");

    const imageInput = document.getElementById('fileToUpload');
    let image = imageInput.files[0];

    const validationAlert = document.getElementById("validationAlert");

    //This sets the alert to be displayed, if none of the below checks are triggered it will be set to none again and
    //therefore be invisible.
    validationAlert.style.display = "block";

    let monthIsChecked = false;
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
    if (image == null || (!image.name.toLowerCase().endsWith(".png")  && !image.name.toLowerCase().endsWith(".jpg")  && !image.name.toLowerCase().endsWith(".jpeg"))) {
        validationAlert.innerHTML = "Please upload a valid image of type png or jpg.";
        return false;
    }

    validationAlert.style.display = "none";
    return true;
}