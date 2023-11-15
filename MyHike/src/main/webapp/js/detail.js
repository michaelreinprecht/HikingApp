//JavaScript for the detail.js page

//TODO explain method
function toggleContent(buttonId) {
    var content = document.getElementById(buttonId + "-content");
    if (content.style.display === "none" || content.style.display === "") {
        content.style.display = "block";
    } else {
        content.style.display = "none";
    }
}

function confirmDelete(event, hikeId) {
    event.preventDefault(); // Verhindert die automatische Formularübermittlung
    if (confirm("Sind Sie sicher, dass Sie diesen Eintrag löschen möchten?")) {
        document.getElementById('deleteForm').submit(); // Formular übermitteln, wenn bestätigt
    }
}
