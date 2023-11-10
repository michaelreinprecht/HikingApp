//JavaScript for the create.js page

//This function displays the image uploaded in the create.jsp Page right after it has been selected.
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