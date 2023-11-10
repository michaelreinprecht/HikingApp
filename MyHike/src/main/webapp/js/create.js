//JavaScript for the create.js page

//Displays the image uploaded in the create.jsp Page right after it has been selected.
function displayImage() {
    var input = document.getElementById('fileToUpload');
    var image = document.getElementById('uploadedImage');

    var file = input.files[0];

    if (file) {
        var reader = new FileReader();

        reader.onload = function(e) {
            image.src = e.target.result;
        };

        reader.readAsDataURL(file);
    }
}

//Validates the information entered into the create.jsp pages form
function validateForm() {
    const namePattern = /^[A-Z0-9].*$/;
    const name = document.getElementById("name");
    const regionPattern = /^[a-zA-Z0-9 ,.'-]+$/;
    const region = document.getElementById("region");
    const startPattern = /^[-+]?([1-8]?\d(\.\d+)?|90(\.0+)?),\s*[-+]?(180(\.0+)?|((1[0-7]\d)|([1-9]?\d))(\.\d+)?)$/;
    const start = document.getElementById("startLocation");
    const endPattern = /^[-+]?([1-8]?\d(\.\d+)?|90(\.0+)?),\s*[-+]?(180(\.0+)?|((1[0-7]\d)|([1-9]?\d))(\.\d+)?)$/;
    const end = document.getElementById("endLocation");
    const descriptionPattern = /^[a-zA-Z0-9 ,.'-]+$/;
    const description = document.getElementById("description");
    const altitudePattern = /^[1-9][0-9]*$/;
    const altitude = document.getElementById("altitude");
    const distancePattern = /^[0-9]+(?:,[0-9]?[0-9])?$/;
    const distance = document.getElementById("distance");
    const durationPattern = /^[0-9]+(?:,[0-9]?[0-9])?$/
    const duration = document.getElementById("duration");
    const radioStrength = document.querySelector('input[name="strength-rating"]:checked');
    const radioStamina = document.querySelector('input[name="stamina-rating"]:checked');
    const radioDifficulty = document.querySelector('input[name="difficulty-rating"]:checked');
    const radioLandscape = document.querySelector('input[name="landscape-rating"]:checked');

    const checkboxes = document.getElementsByClassName("form-check-input");
    const validationAlert = document.getElementById("validationAlert");

    let checked = false;
    for (let i = 0; i < checkboxes.length; i++) {
        if (checkboxes[i].checked) {
            checked = true;
        }
    }
    if (!checked) {
        validationAlert.innerHTML = "Please pick at least one recommended month.";
        validationAlert.style.display = "block";
        return false;
    }
    if (!namePattern.test(name.value)){
        validationAlert.innerHTML = "Please select a name.";
        validationAlert.display = "block";
        return false;
    }
    if (!regionPattern.test(region.value)){
        validationAlert.innerHTML = "Please select a region.";
        validationAlert.display = "block";
        return false;
    }
    if (!startPattern.test(start.value)){
        validationAlert.innerHTML = "Please enter a valid starting lon-coordinate.";
        validationAlert.display = "block";
        return false;
    }
    if (!endPattern.test(end.value)){
        validationAlert.innerHTML = "Please enter a valid ending lon-coordinate.";
        validationAlert.display = "block";
        return false;
    }
    if (!descriptionPattern.test(description.value)){
        validationAlert.innerHTML = "Please enter a valid description.";
        validationAlert.display = "block";
        return false;
    }
    if (!altitudePattern.test(altitude.value)){
        validationAlert.innerHTML = "Please enter a valid altitude in meters.";
        validationAlert.display = "block";
        return false;
    }
    if (!distancePattern.test(distance.value)){
        validationAlert.innerHTML = "Please enter a valid distance in kilometers.";
        validationAlert.display = "block";
        return false;
    }
    if (!durationPattern.test(duration.value)){
        validationAlert.innerHTML = "Please enter a valid distance in hours.";
        validationAlert.display = "block";
        return false;
    }
    if (!radioLandscape) {
        validationAlert.innerHTML = "Please rate the landscape.";
        validationAlert.display = "block";
        return false;
    }
    if (!radioStrength) {
        validationAlert.innerHTML = "Please rate the required strength for this hike.";
        validationAlert.style.display = "block";
        return false;
    }
    if (!radioStamina) {
        validationAlert.innerHTML = "Please rate the required stamina for this hike.";
        validationAlert.display = "block";
        return false;
    }
    if (!radioDifficulty) {
        validationAlert.innerHTML = "Please rate this hikes difficulty.";
        validationAlert.display = "block";
        return false;
    }
    return true;
}