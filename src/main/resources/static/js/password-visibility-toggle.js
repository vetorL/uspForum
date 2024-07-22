const visibilityToggleButtons =
    document.getElementsByClassName("password-visibility-toggle-button");

Array.from(visibilityToggleButtons).forEach(element =>
    element.addEventListener("click", toggleVisibility));

function toggleVisibility(event) {

    const svg1 = event.currentTarget.firstElementChild;
    const svg2 = event.currentTarget.firstElementChild.nextElementSibling;

    const input = event.currentTarget.previousElementSibling;

    if(svg1.classList.contains("password-visibility-off")) {

        svg1.classList.remove("password-visibility-off");
        svg2.classList.add("password-visibility-off")

        input.type = "password";

    } else {

        svg2.classList.remove("password-visibility-off");
        svg1.classList.add("password-visibility-off");

        input.type = "text";

    }
}