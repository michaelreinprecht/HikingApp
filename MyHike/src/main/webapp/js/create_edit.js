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
    //This input is empty if there are not at least 2 valid points selected.
    const routeCoordinatesString = document.getElementById("route-coordinates").value;

    //Pattern matching for altitude, needs to be either a 0 or any other number from 1-9 followed by additional digits from 0-9.
    const altitudePattern = /^(0|[1-9][0-9]*)$/;
    const altitude = document.getElementById("altitude").value;

    //Pattern matching for distance, needs to be at least one number from 0-9, CAN OPTIONALLY be followed by a dot (.)
    //and 2 decimals (lowest possible is 0.00km).
    const distancePattern = /^[0-9]+(?:.[0-9]?[0-9])?$/;
    const distance = document.getElementById("distance").value;

    const duration = document.getElementById("duration").value;

    const radioStrength = document.querySelector('input[name="strength-rating"]:checked');
    const radioStamina = document.querySelector('input[name="stamina-rating"]:checked');
    const radioDifficulty = document.querySelector('input[name="difficulty-rating"]:checked');
    const radioLandscape = document.querySelector('input[name="landscape-rating"]:checked');

    const checkboxes = document.getElementsByClassName("form-check-input");

    //Get the first selected file (we can only select one file)
    const imageInput = document.getElementById('fileToUpload');
    let image = imageInput.files[0];

    //The alert which will be displayed if validation fails.
    const validationAlert = document.getElementById("validationAlert");

    //This sets the alert to be displayed, if none of the below checks are triggered it will be set to none again and
    //therefore be invisible.
    validationAlert.style.display = "block";

    if (routeCoordinatesString === null || routeCoordinatesString === "") {
        validationAlert.innerHTML = "Please select at least a starting and ending point for your hike."
        return false;
    }

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
    //Altitude/Distance can be null or empty, if they are not they have to match their respective patterns.
    if (!altitudePattern.test(altitude)){
        validationAlert.innerHTML = "Please enter a valid altitude in meters.";
        return false;
    }
    if (!distancePattern.test(distance)){
        validationAlert.innerHTML = "Please enter a valid distance in kilometers.";
        return false;
    }
    if (duration === null || duration === "") {
        validationAlert.innerHTML = "Please enter a valid duration in between 00:00 - 23:59 hours.";
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
