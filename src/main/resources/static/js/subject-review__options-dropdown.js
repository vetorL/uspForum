/* When the user clicks on the button,
toggle between hiding and showing the dropdown content */
function dropdown(e) {
    console.log(e.nextElementSibling);
    e.nextElementSibling.classList.toggle("show");
}

// Close the dropdown menu if the user clicks outside of it
window.onclick = function(event) {
    if (!event.target.matches('.options-dropdown__button')) {
        var dropdowns = document.getElementsByClassName("subject-review__options-dropdown");
        var i;
        for (i = 0; i < dropdowns.length; i++) {
            var openDropdown = dropdowns[i];
            if (openDropdown.classList.contains('show')) {
                openDropdown.classList.remove('show');
            }
        }
    }
}