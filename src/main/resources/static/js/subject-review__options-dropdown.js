/* When the user clicks on the button,
toggle between hiding and showing the dropdown content */
function dropdown(e) {
    e.nextElementSibling.classList.toggle("show");

    // This invisible background is for capturing clicks outside the dropdown
    const optionsDropdownBackground = e.nextElementSibling.nextElementSibling;
    optionsDropdownBackground.style.display = "block";
}

// Close the dropdown menu if the user clicks outside of it
window.onclick = function(event) {
    if (event.target.classList.contains("options-dropdown__background")) {
        let dropdowns = document.getElementsByClassName("subject-review__options");
        Array.from(dropdowns).forEach(element => {
            if (element.classList.contains('show')) {
                element.classList.remove('show');
            }
        });

        let dropdownBackgrounds =
            document.getElementsByClassName("options-dropdown__background");

        Array.from(dropdownBackgrounds).forEach(element => {
            element.style.display = "none";
        });
    }

    // When the user clicks anywhere outside the modal, close it
    if (event.target == deleteModal) {
        deleteModal.style.display = "none";
    } else if (event.target == editModal) {
        editModal.style.display = "none";
    }
}

// Get the modals
const deleteModal = document.getElementById("deleteReviewModal");
const editModal = document.getElementById("editReviewModal");

// Get the buttons that open the modals
let modalButtons = document.getElementsByClassName("subject-review__option");

// When the user clicks a button, open the correct modal and set its hidden input's value to the id of the review
// associated with the button that was clicked
Array.from(modalButtons).forEach(element => element.addEventListener(
    "click", function(e) {
        if(e.currentTarget.innerText === "Editar") {
            editModal.style.display = "block";
            document.getElementById("editReviewId").value = e.currentTarget.dataset.associatedReviewId;
        } else if (e.currentTarget.innerText === "Deletar") {
            deleteModal.style.display = "block";
            document.getElementById("deleteReviewId").value = e.currentTarget.dataset.associatedReviewId;
        }
    }
));

// Get the <span> element that closes the modal
const modalCloseSpanElements = document.getElementsByClassName("close");

// When the user clicks on a <span> (x), close all modals
Array.from(modalCloseSpanElements).forEach(element => element.addEventListener("click", closeModals))

// ### Listen for "submit" event triggering from the modal form elements ###

// Listen for "submit" event triggering from the deletion modal
document.getElementById("deleteReviewModalForm").addEventListener("submit", ev => {
    ev.preventDefault();

    const formData = new FormData(ev.target);

    if(formData.get("insurance") !== "deletar") {
        return;
    }

    const associatedReviewId = formData.get("reviewId");

    const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

    const url = 'http://localhost:8080/api/v1/reviews/' + associatedReviewId;

    fetch(url, {
        method: 'DELETE',
        credentials: 'include',
        headers: {
            [csrfHeader]: csrfToken,
            'charset': 'UTF-8',
            'Content-Type': 'application/json'
        }
    }).then(response => {
        if(response.ok) {
            onSuccessfulReviewDeletion(associatedReviewId);
        }
    });
});

// Closes all modals by making their display property equal to "none"
function closeModals() {
    deleteModal.style.display = "none";
    editModal.style.display = "none";
    // reportModal.style.display = "none";
}

function onSuccessfulReviewDeletion(associatedReviewId) {
    // Close dropdown
    let dropdowns = document.getElementsByClassName("subject-review__options");
    Array.from(dropdowns).forEach(element => {
        if (element.classList.contains('show')) {
            element.classList.remove('show');
        }
    });

    // Close modal
    deleteModal.style.display = "none";

    // Remove subject-review fragment
    document.getElementById("subject-review-" + associatedReviewId).remove();
}