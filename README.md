# uspForum
A forum for USP

## Todo list

### Things that require third party APIs
- usp email confirmation on sign up
- image storage (AWS S3)
- file storage (AWS S3)
- image upload (AWS)
- account deletion (need email confirmation)
- change password (need email confirmation)

### Optimizations
- query results based on derived value (specifically votes associated with subject-review for ordering)
- restrict number of login attempts and create throttling delays

### Brainstorming
- section of the website for guides
- section of the website for international exchange programs
- user toggle option for "complete" anonymity
- solve problem of Ciclo Basico (how many of them are out there that I don't know about?)
- creation section of the website (for creating subjects, professors and courses)
- add section of the website for all advertised products

### Just work
- subject file tab (Provas, Trabalhos & Listas)
- pagination of subject reviews
- image logo
- add modal for review substitution confirmation
- profile configuration page (for changing username, profile picture, and toggling options)
- validate review post request (controller and view)
- edit subject-review (when edited reset vote count and delete associated votes, but do not update rep)
- register user course
- display user's course and campus
- create problem reporting page
- fix subject creation
- add report review option
- option modals in profile