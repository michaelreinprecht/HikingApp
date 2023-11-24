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
        let hikeId = $(this).data('hike-id');

        //Get values from parameters
        //If hike.getHikeId is null set hikeId to "", else set it to hike.getHikeId()
        let poiTitle = document.getElementById("poiTitle").value;
        let poiDescription = document.getElementById("poiDescription").value;
        let poiLon = document.getElementById("poiLon").value;
        let poiLat = document.getElementById("poiLat").value;
        let poiImageInput = document.getElementById('poiImage');
        let poiFile = poiImageInput.files[0];
        let poiImageSource;
        if (poiFile) {
            let reader = new FileReader();

            reader.onload = function (e) {
                poiImageSource = e.target.result;
            };

            reader.readAsDataURL(poiFile);
        }

        let formData = new FormData();

        // Append text data
        formData.append('hikeId', hikeId);
        formData.append('poiTitle', poiTitle);
        formData.append('poiDescription', poiDescription);
        formData.append('poiLon', poiLon);
        formData.append('poiLat', poiLat);
        formData.append('poiImage', poiFile);

        let result = document.getElementById("result");
        $.ajax({
            type: "POST",
            url: "addPOIServlet", // Servlet URL
            processData: false,
            contentType: false,
            enctype: "multipart/form-data",
            data: formData,
            success: function (response) {
                console.log(response);

                /*

                 */

                // Create a new div element
                let card = document.createElement("div");

                // Set attributes for the div
                card.id = response + "-POICard";
                card.className = "card"
                card.style.width = "45%";
                card.style.margin = "2.5%";

                // Create an image element
                let imgElement = document.createElement("img");
                imgElement.className = "card-img-top";
                imgElement.src = poiImageSource;
                imgElement.alt = "";

                let cardBottom = document.createElement("div");
                cardBottom.style.margin = "2%";

                // Create a title element
                let titleElement = document.createElement("h5");
                titleElement.className = "card-title";
                titleElement.textContent = poiTitle;

                // Create a paragraph element
                let paragraphElement = document.createElement("p");
                paragraphElement.className = "card-text";
                paragraphElement.textContent = poiDescription;

                // Create a link element (a) for the button
                let buttonElement = document.createElement("button");
                buttonElement.name = "deletePOIButton";
                buttonElement.className = "btn btn-danger";
                buttonElement.style.width = "90%";
                buttonElement.style.alignSelf = "end";
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
            },
        });
    })
})

function deletePOI(poiId) {
    let formData = new FormData();

    let poiCard = document.getElementById(poiId + "-POICard");

    // Append text data
    formData.append('poiId', poiId);

    let result = document.getElementById("result");

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
        },
    });
}

$(document).ready(function () {
    $('[name="deletePOIButton"]').click(function () {
        let poiId = $(this).data('poi-id');
        deletePOI(poiId);
    })
})



