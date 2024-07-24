// Intercepts form submit and sends POST request to the API
document.getElementById("subject-review-form").addEventListener("submit", sendPostHttpRequest);

function sendPostHttpRequest(event) {
    event.preventDefault();

    const formData = new FormData(event.target);

    const associatedSubjectId = formData.get("subjectId");

    const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

    const url = 'http://localhost:8080/api/v1/subject/' + associatedSubjectId + '/reviews';

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
        // Reload the page
        location.reload();
    });
}