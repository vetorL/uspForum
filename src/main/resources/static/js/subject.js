let buttonForms = document.getElementsByClassName("vote-button-form");


Array.from(buttonForms).forEach(elem => {elem.addEventListener(
    "submit", function (e) {
        e.preventDefault();

        const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
        const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

        const formData = new FormData(e.target);

        const voteData = {
            subjectReviewId: formData.get("subjectReviewId"),
            vote: formData.get("vote")
        }

        fetch('http://localhost:8080/disciplina/votar', {
            method: 'POST',
            credentials: 'include',
            headers: {
                [csrfHeader]: csrfToken,
                'charset': 'UTF-8',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(voteData)
        }).then(res => console.log(res)).catch(err => console.log(err));
    }
)})