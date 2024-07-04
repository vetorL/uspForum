// Calling the API

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

// Color Handling

let voteUpButtons = document.getElementsByClassName("vote-up");
let voteDownButtons = document.getElementsByClassName("vote-down");

Array.from(voteUpButtons).forEach(el => el.addEventListener('click', handleClickUp));

Array.from(voteDownButtons).forEach(el => el.addEventListener('click', handleClickDown));

function handleClickUp(e) {

    let reviewId = e.currentTarget.id;
    reviewId = reviewId.split("-")[2];

    let sibling = document.getElementById("vote-down-" + reviewId);

    if(sibling.classList.contains("vote-pressed-down")) {
        sibling.classList.remove("vote-pressed-down");
    }

    if(e.currentTarget.classList.contains("vote-pressed-up")) {
        e.currentTarget.classList.remove("vote-pressed-up");
    } else {
        e.currentTarget.classList.add("vote-pressed-up");
    }
}

function handleClickDown(e) {

    let reviewId = e.currentTarget.id;
    reviewId = reviewId.split("-")[2];

    let sibling = document.getElementById("vote-up-" + reviewId);

    console.log(sibling);

    if(sibling.classList.contains("vote-pressed-up")) {
        sibling.classList.remove("vote-pressed-up");
    }

    if(e.currentTarget.classList.contains("vote-pressed-down")) {
        e.currentTarget.classList.remove("vote-pressed-down");
    } else {
        e.currentTarget.classList.add("vote-pressed-down");
    }
}