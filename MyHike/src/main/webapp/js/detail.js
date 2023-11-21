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







