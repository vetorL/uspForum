// Listen for "submit" event triggering from the archive form in the admin page
const adminArchiveForms = document.getElementsByClassName("admin-archive-report");
if(adminArchiveForms != null) {
    Array.from(adminArchiveForms).forEach(element => element.addEventListener(
        "submit", function (event) {
            event.preventDefault();

            const formData = new FormData(event.target);

            const associatedReviewId = formData.get("reportId");

            sendArchivePatchRequest(associatedReviewId);

        }));
}

// Sends a PATCH http request
function sendArchivePatchRequest(associatedReviewId) {
    const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

    const url = 'http://localhost:8080/api/v1/reports/' + associatedReviewId + '/archive';

    fetch(url, {
        method: 'PATCH',
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