let voteUpButtons = document.getElementsByClassName("vote-up");
let voteDownButtons = document.getElementsByClassName("vote-down");

Array.from(voteUpButtons).forEach(el => el.addEventListener('click', handleClickUp));

Array.from(voteDownButtons).forEach(el => el.addEventListener('click', handleClickDown));

function handleClickUp(e) {

    let sibling = getSibling(e, "vote-down");

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

    let sibling = getSibling(e, "vote-up");

    if(sibling.classList.contains("vote-pressed-up")) {
        sibling.classList.remove("vote-pressed-up");
    }

    if(e.currentTarget.classList.contains("vote-pressed-down")) {
        e.currentTarget.classList.remove("vote-pressed-down");
    } else {
        e.currentTarget.classList.add("vote-pressed-down");
    }
}

function getSibling(e, siblingClass) {
    let sibling = e.target.parentNode.firstElementChild;

    while(sibling) {
        if (sibling.nodeType === 1 && sibling !== e) {
            if(sibling.classList.contains(siblingClass)) {
                return sibling;
            }
            sibling = sibling.nextElementSibling;
        }
    }

    return null;
}