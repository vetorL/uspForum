// Listen for "submit" event triggering from the deletion modal
const deleteReviewModalForm = document.getElementById("deleteReviewModalForm");
if (deleteReviewModalForm != null) {
    document.getElementById("deleteReviewModalForm").addEventListener("submit", event => {
        event.preventDefault();

        const formData = new FormData(event.target);

        if(formData.get("insurance") !== "deletar") {
            return;
        }

        const associatedReviewId = formData.get("reviewId");

        sendDeleteHttpRequest(event, associatedReviewId);
    });
}

// Listen for "submit" event triggering from the admin page
let adminDeleteForms = document.getElementsByClassName("admin-delete-review");
if(adminDeleteForms != null) {
    Array.from(adminDeleteForms).forEach(element => element.addEventListener(
        "submit", function (event) {
            event.preventDefault();

            const formData = new FormData(event.target);

            const associatedReviewId = formData.get("reviewId");

            sendDeleteHttpRequest(event, associatedReviewId);

    }));
}

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
            // Reload the page
            location.reload();
        }
    });
}