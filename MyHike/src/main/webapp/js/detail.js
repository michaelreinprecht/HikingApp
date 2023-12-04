//JavaScript for the detail.js page

//TODO explain method
function toggleContent(buttonId) {
    let content = document.getElementById(buttonId + "-content");
    if (content.style.display === "none" || content.style.display === "") {
        content.style.display = "block";
    } else {
        content.style.display = "none";
    }
}

//Makes sure that the code is only executed one the entire windows is loaded (for example images)
window.onload = function() {
    let deleteButton = document.getElementById('deleteButton');
    if (deleteButton) {
        //Call confirm delete on button press
        deleteButton.onclick = function(event) {
            confirmDelete(event);
        };
    }
};

//Second confirmation, making sure that the user really wants to delete the hike.
function confirmDelete(event) {
    event.preventDefault();
    if (confirm("Sind Sie sicher, dass Sie diesen Eintrag löschen möchten?")) {
        document.getElementById('deleteForm').submit();
    }
}

function displayImage() {
    let input = document.getElementById('poiImage');
    let image = document.getElementById('imgDisplay');

    let file = input.files[0];

    if (file) {
        let reader = new FileReader();

        reader.onload = function(e) {
            image.src = e.target.result;
        };

        reader.readAsDataURL(file);
    }
}

$(document).ready(function () {
    $('#addPOIButton').click(function () {
        hideSuccess();
        displayLoading();

        //This is where the created cards will be appended.
        let result = document.getElementById("result");

        //ID of the hike the POI is related to.
        let hikeId = $(this).data('hike-id');

        //Get values from parameters
        //If hike.getHikeId is null set hikeId to "", else set it to hike.getHikeId()
        let poiTitle = document.getElementById("poiTitle").value;
        let poiDescription = document.getElementById("poiDescription").value;
        let poiLon = document.getElementById("poiLon").value;
        let poiLat = document.getElementById("poiLat").value;
        let poiImageInput = document.getElementById('poiImage');
        let poiFile = poiImageInput.files[0];

        //Regex validation of inputs.
        if (!validatePOI()) {
            hideLoading();
            return false;
        }

        //Get imageSource from File to display the new Card with the image
        let poiImageSource;
        if (poiFile) {
            let reader = new FileReader();

            reader.onload = function (e) {
                poiImageSource = e.target.result;
            };

            reader.readAsDataURL(poiFile);
        }

        //Create formData to send to our addPOIServlet
        let formData = new FormData();
        formData.append('hikeId', hikeId);
        formData.append('poiTitle', poiTitle);
        formData.append('poiDescription', poiDescription);
        formData.append('poiLon', poiLon);
        formData.append('poiLat', poiLat);
        formData.append('poiImage', poiFile);

        //Using an ajax call to call our addPOIServlet asynchronously
        $.ajax({
            type: "POST",
            url: "addPOIServlet", // Servlet URL
            processData: false,
            contentType: false,
            enctype: "multipart/form-data",
            data: formData,
            success: function (response) {
                // Generate a new -POICard element!
                // Create a new div element
                let card = document.createElement("div");

                // Set attributes for the div
                card.id = response + "-POICard";
                card.className = "card bg-light"
                card.style.width = "24.25%";
                card.style.marginTop = "20px";

                // Create an image element
                let imgElement = document.createElement("img");
                imgElement.className = "card-img-top";
                imgElement.src = poiImageSource;
                imgElement.alt = "";
                imgElement.style.maxHeight = "150px";
                imgElement.style.objectFit = "cover";

                let cardBottom = document.createElement("div");
                cardBottom.className = "d-flex flex-column";
                cardBottom.style.margin = "2%";
                cardBottom.style.height = "100%";

                // Create a title element
                let titleElement = document.createElement("h5");
                titleElement.className = "card-title";
                titleElement.textContent = poiTitle;

                // Create a paragraph element
                let paragraphElement = document.createElement("p");
                paragraphElement.className = "card-text";
                paragraphElement.innerHTML = poiDescription + "<br>" + "Lon: " + poiLon + "  Lat: " + poiLat;
                paragraphElement.style.fontSize = "12px";

                // Create a link element (a) for the button
                let buttonElement = document.createElement("button");
                buttonElement.name = "deletePOIButton";
                buttonElement.className = "btn btn-danger";
                buttonElement.style.width = "90%";
                buttonElement.style.alignSelf = "center";
                buttonElement.style.marginTop = "auto";
                buttonElement.textContent = "Delete";
                //Allow button to delete
                buttonElement.onclick = function () {
                    deletePOI(response)
                };
                //The buttons poi-id has to be set like this since the hike id is a non-standard attribute.
                buttonElement.setAttribute("data-poi-id", response);

                // Append the child elements to the card and to result div
                cardBottom.appendChild(titleElement);
                cardBottom.appendChild(paragraphElement);
                cardBottom.appendChild(buttonElement);
                card.appendChild(imgElement);
                card.appendChild(cardBottom);
                result.appendChild(card);
                hideLoading();
                displaySuccess();
            },
            error: function(response) {
                hideLoading();
                const validationAlert = document.getElementById("validationAlert");
                validationAlert.innerHTML = "An unexpected error has occurred creating your point of interest. Please make " +
                    "sure you are connected to the internet or try again later. Error: " + response;
                validationAlert.style.display = "block";
            }
        });
    })
})

//Delete POI
//If called from an already existing poi-card, the poiId cannot be passed and needs to be gotten from data-poi-id attribute.
$(document).ready(function () {
    $('[name="deletePOIButton"]').click(function () {
        let poiId = $(this).data('poi-id');
        deletePOI(poiId);
    })
})
//If called directly from a newly created poiId, the name attribute does not take effect until the page is refreshed,
//therefore the poiId is passed in this case (when creating the deletePOIButtoh).
function deletePOI(poiId) {
    hideSuccess();
    displayLoading();

    //Append the poiId to the formData, which will be sent to the servlet via the ajax call.
    let formData = new FormData();
    let poiCard = document.getElementById(poiId + "-POICard");
    formData.append('poiId', poiId);
    $.ajax({
        type: "POST",
        url: "deletePOIServlet", // Servlet URL
        processData: false,
        contentType: false,
        enctype: "multipart/form-data",
        data: formData,
        success: function (response) {
            console.log(response);
            poiCard.remove();
            hideLoading();
        },
        error: function(response) {
            hideLoading();
            const validationAlert = document.getElementById("validationAlert");
            validationAlert.innerHTML = "An unexpected error has occurred deleting your point of interest. Please make " +
                "sure you are connected to the internet or try again later. Error: " + response;
            validationAlert.style.display = "block";
        }
    });
}

//Returns false and displays a validation alert if validation fails, if validation is passed returns true
function validatePOI() {
    //The alert which will be displayed if validation fails.
    const validationAlert = document.getElementById("validationAlert");

    //Pattern matching for lat and lon coordinates, checks if lat and lon are in a certain range (lat -90 to 90, lon
    // -180 to 180), allows a maximum of 6 decimal points
    const LatPattern = /^[-+]?([1-8]?\d(\.\d{1,6})?|90(\.0{1,6})?)$/;
    const LonPattern = /^[-+]?(180(\.0{1,6})?|((1[0-7]\d|\d{1,2})(\.\d{1,6})?))$/;

    //Get the values to validate
    const lon = document.getElementById("poiLon").value;
    const lat = document.getElementById("poiLat").value;
    const imageInput = document.getElementById('poiImage');
    let image = imageInput.files[0];

    validationAlert.style.display = "block";

    //Validate Lon and Lat based on regex.
    if (!LonPattern.test(lon)){
        validationAlert.innerHTML = "Please enter a valid starting lon-coordinate (ranges from -180.00000 to 180.000000).";
        return false;
    }
    if (!LatPattern.test(lat)){
        validationAlert.innerHTML = "Please enter a valid starting lat-coordinate (ranges from -90.00000 to 90.000000).";
        return false;
    }
    //Check if image is given and of proper type.
    if (image == null || (!image.name.toLowerCase().endsWith(".png")  && !image.name.toLowerCase().endsWith(".jpg")  && !image.name.toLowerCase().endsWith(".jpeg"))) {
        validationAlert.innerHTML = "Please upload a valid image of type png or jpg.";
        return false;
    }
    validationAlert.style.display = "none";
    return true;
}

//Displays successAlert
function displaySuccess() {
    const successAlert = document.getElementById("successAlert");
    successAlert.style.display = "block";
}

//Hides successAlert
function hideSuccess() {
    const successAlert = document.getElementById("successAlert");
    successAlert.style.display = "none";
}

//Displays loading gif for POI adding.
function displayLoading() {
    const loadingDiv = document.getElementById("loadingDiv");
    loadingDiv.style.display = 'block';
}

//Hides loading gif for POI adding.
function hideLoading() {
    const loadingDiv = document.getElementById("loadingDiv");
    loadingDiv.style.display = 'none';
}



