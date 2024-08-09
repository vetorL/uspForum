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