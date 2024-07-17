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
}