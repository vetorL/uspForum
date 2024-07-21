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
        const reviewId = e.currentTarget.dataset.associatedReviewId;

        if(e.currentTarget.innerText === "Editar") {
            editModal.style.display = "block";
            document.getElementById("editReviewId").value = reviewId;

            // # Populate inputs for editing #

            // Populate title
            document.getElementById("edit-review__title-input").value =
                document.getElementById("subject-review-title-" + reviewId).innerText;

            // Populate content
            document.getElementById("edit-review__content-input").value =
                document.getElementById("subject-review-content-" + reviewId).innerText;

            // Populate recommendation
            document.getElementById("edit-review__recommendation-input").value =
                document.getElementById("subject-review-recommendation-" + reviewId).innerText;

        } else if (e.currentTarget.innerText === "Deletar") {
            deleteModal.style.display = "block";
            document.getElementById("deleteReviewId").value = reviewId;
        }
    }
));

// Get the <span> element that closes the modal
const modalCloseSpanElements = document.getElementsByClassName("close");

// Get the cancel button
const modalCancelButtons = document.getElementsByClassName("modal-cancel");

// When the user clicks on a <span> (x), close all modals
Array.from(modalCloseSpanElements).forEach(element => element.addEventListener("click", closeModals));

// When the user clicks on a cancel button, close all modals
Array.from(modalCancelButtons).forEach(element => element.addEventListener("click", closeModals));

// ### Listen for "submit" event triggering from the modal form elements ###

// Listen for "submit" event triggering from the deletion modal
document.getElementById("deleteReviewModalForm").addEventListener("submit", event => {
    event.preventDefault();

    const formData = new FormData(event.target);

    if(formData.get("insurance") !== "deletar") {
        return;
    }

    const associatedReviewId = formData.get("reviewId");

    sendDeleteHttpRequest(event, associatedReviewId);
});

// Listen for "submit" event triggering from the edit modal
document.getElementById("editReviewModalForm").addEventListener("submit", event => {
    event.preventDefault();

    const formData = new FormData(event.target);

    const associatedReviewId = formData.get("reviewId");

    sendDeleteHttpRequest(event, associatedReviewId);
    sendPostHttpRequest(formData, associatedReviewId);
});

// Sends a DELETE http request and if response OK calls onSuccessfulReviewDeletion
function sendDeleteHttpRequest(event, associatedReviewId) {
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
        if (response.ok) {
            onSuccessfulRequest(associatedReviewId);

            // Remove subject-review fragment
            document.getElementById("subject-review-" + associatedReviewId).remove();
        }
    });
}

// Send a POST http request for posting a subject review
function sendPostHttpRequest(formData, associatedReviewId) {
    const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

    const url = 'http://localhost:8080/api/v1/reviews/' + associatedReviewId;

    const subjectReviewDTO = {
        title: formData.get("title"),
        content: formData.get("content"),
        recommendation: formData.get("recommendation")
    }

    fetch(url, {
        method: 'POST',
        credentials: 'include',
        headers: {
            [csrfHeader]: csrfToken,
            'charset': 'UTF-8',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(subjectReviewDTO)
    }).then(response => {
        if (response.ok) {
            console.log(response);
        }
    });
}

// Closes all modals by making their display property equal to "none"
function closeModals() {
    deleteModal.style.display = "none";
    editModal.style.display = "none";
    // reportModal.style.display = "none";
}

function onSuccessfulRequest(associatedReviewId) {
    // Close dropdown
    let dropdowns = document.getElementsByClassName("subject-review__options");
    Array.from(dropdowns).forEach(element => {
        if (element.classList.contains('show')) {
            element.classList.remove('show');
        }
    });

    // Close all modals
    closeModals();
}