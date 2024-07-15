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
- query results based on derived value (specifically rep associated with podium)
- solve code redundancy in subject-review form
- restrict number of login attempts and create throttling delays

### Brainstorming
- section of the website for guides
- section of the website for international exchange programs
- user toggle option for "complete" anonymity

### Just work
- subject file tab
- pagination of subject reviews
- image logo
- add modal for review substitution confirmation
- profile configuration page (for changing username, profile picture, and toggling options)
- validate review post request (controller and view)
- unique constraint for a given subject and professor
- delete subject-review (do not delete associated votes)
- edit subject-review (when edited reset vote count, but do not delete associated votes)
- allow voting from profile page
- register user course
- display user's course and campus
- display subject-review posted date
- create problem reporting page
- unique constraint for a given campus and course
- fix subject creation
- make subject-review in profile link to the subject