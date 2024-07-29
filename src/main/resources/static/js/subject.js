// Update .subject-review-form__character-counter as user writes
const contentInput = document.getElementById("subject-review-form__content-input");
const editContentInput = document.getElementById("edit-review__content-input");
const postButton = document.getElementById("subject-review-form-post-button");

if(contentInput != null) {
    contentInput.addEventListener("input", updateCharacterCounter);
}

if (editContentInput != null) {
    editContentInput.addEventListener("input", updateCharacterCounter);
}

const titleInput = document.getElementById("subject-review-form__title-input");
const editTitleInput = document.getElementById("edit-review__title-input")
if(titleInput != null) {
    titleInput.addEventListener("input", updateCharacterCounter);
}

if (editTitleInput != null) {
    editTitleInput.addEventListener("input", updateCharacterCounter);
}

function updateCharacterCounter(event) {

    const current_number_of_characters = event.currentTarget.value.length;

    const span_character_counter = event.currentTarget.nextElementSibling;

    let max_size = span_character_counter.innerText.split("/")[1];

    span_character_counter.innerText = current_number_of_characters + "/" + max_size;

    if(current_number_of_characters > parseInt(max_size)) {

        // The user has written more characters than allowed, give a visual warning
        span_character_counter.style.color = "red";
        event.currentTarget.style.color = "red";

        // Disable the post button
        postButton.disabled = true;

    } else {

        // The user is within the character limit
        span_character_counter.style.color = "gray";
        event.currentTarget.style.color = "black";

        // Enable the post button
        postButton.disabled = false;

    }

}

document.addEventListener("DOMContentLoaded", function (event) {
    var scrollpos = sessionStorage.getItem('scrollpos');
    if (scrollpos) {
        window.scrollTo(0, scrollpos);
        sessionStorage.removeItem('scrollpos');
    }
});

window.addEventListener("beforeunload", function (e) {
    sessionStorage.setItem('scrollpos', window.scrollY);
});

// Handle sorting
document.getElementById("subject-reviews-filter").addEventListener("input", sort);

function sort(event) {

    const currentURL = window.location.href;

    if (event.currentTarget.value === "recentes") {

        if(currentURL.includes("?sort")) {
            return;
        }

        // Simulate an HTTP redirect:
        window.location.replace(currentURL + "?sort=recentes");

    } else {

        if(currentURL.includes("?sort")) {
            const newURL = currentURL.split("?sort")[0];
            // Simulate an HTTP redirect:
            window.location.replace(newURL);
        }

    }

}