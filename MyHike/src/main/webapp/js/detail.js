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


//stellt sicher, dass der Code erst dann ausgeführt wird, wenn das gesamte Fenster geladen ist(Bilder z.B)
window.onload = function() {
    let deleteButton = document.getElementById('deleteButton'); // Holt den Delete Button durch seine ID(detail.jsp)
    if (deleteButton) { // prüft ob er überhaupt existiert
        // Binden eines 'click' Event-Handlers an den Button
        // Wenn der Button geklickt wird, wird die 'confirmDelete' Funktion aufgerufen
        deleteButton.onclick = function(event) {
            confirmDelete(event);
        };
    }
};

function confirmDelete(event) {
    event.preventDefault();
    if (confirm("Sind Sie sicher, dass Sie diesen Eintrag löschen möchten?")) {
        document.getElementById('deleteForm').submit();
    }
}







