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
    if (event.target == modal) {
        modal.style.display = "none";
    }
}

// Get the modal
const modal = document.getElementById("deleteReviewModal");

// Get the buttons that open the modal
let modalButtons = document.getElementsByClassName("subject-review__option");

// When the user clicks the button, open the modal and set its hidden input's value to the id of the review associated
Array.from(modalButtons).forEach(element => element.addEventListener(
    "click", function(e) {
        modal.style.display = "block";
        document.getElementById("deleteReviewId").value = e.currentTarget.dataset.associatedReviewId;
    }
));

// Get the <span> element that closes the modal
const span = document.getElementsByClassName("close")[0];

// When the user clicks on <span> (x), close the modal
span.onclick = function() {
    modal.style.display = "none";
}


document.getElementById("modalDeleteReview").addEventListener("submit", ev => {
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

function onSuccessfulReviewDeletion(associatedReviewId) {
    // Close dropdown
    let dropdowns = document.getElementsByClassName("subject-review__options");
    Array.from(dropdowns).forEach(element => {
        if (element.classList.contains('show')) {
            element.classList.remove('show');
        }
    });

    // Close modal
    modal.style.display = "none";

    // Remove subject-review fragment
    document.getElementById("subject-review-" + associatedReviewId).remove();
}