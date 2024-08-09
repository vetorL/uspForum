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