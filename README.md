
Demo site - [LINK](http://185.167.96.37:8080/index)

Demo users: boriss/pass1234 (Organizer), jane/pass1234 (regular user)

# The main system functions

Events are created and edited by organizers (users with special roles).
Commenting on events by users who are logged in.
Signing up for events.
Event search engine.
API for other websites/services willing to present events.

## General guidelines

Building a website using Spring, JPA (Hibernate), and Thymeleaf as the view layer. Introducing the division in the 
application into DAO, services, controllers (REST controllers in the case of using API) and placing the appropriate logic 
into each of them. Securing access to applications and functionalities using Spring Security.

## Functionalities

Bootstrap is attached to each subsequent page using Thymeleaf, in the upper section of the page, there is the name of the 
website and the login and register buttons.

## User registration

The registration form contains:
Name to display - the field cannot be empty or contain all white characters, and the maximum length is 50 characters. 
Login (email) – checking for the correctness of the e-mail.
Password – must be at least 8 characters long, but no more than 30 (the user should confirm the entered password).

The user with a given email can register only once. 
The user should be associated with the roles in the system, which will include two cases: an organizer and an ordinary user. 
Each registered person is always assigned the role of "regular user". The password is kept in the database in the form which 
prevents it from being displayed/recovered.

## User login

Login form containing login(email) and password. Logging in using Spring Security. 
After the user has successfully logged in, he/she will be redirected to the home page. Here instead of the login/register
buttons, information such as: "Logged in as 'user email'" will be displayed.

## Adding a new event

The event has the following:
Title - the field cannot be empty or contain only white characters.
Dates from/to - mandatory.
Description - a minimum of 20 characters.
Link to event's image - can be skipped.
The event will be associated with the user who adds it.

## Editing/Deleting an event

An additional page that will allow to edit/delete the created event.
Only the owner of the event can edit or delete it.
These options will appear on the event's details page and My events page.

## List of events

In the central part of the webpage, a list of all current events is placed. 
Each element of the list contains:
Highlighted header with the title of the event.
Dates from/to the event.
The first 50 characters of the description.

Events are sorted in chronological order.

## Event search engine

The form containing at the top of the home page:
Text field for entering the phrase, dropdown field: future, ongoing and future, and all, the "search" button.
The entered phrase will be searched in the stored events titles.
Search results will be placed on a separate page, in the same layout as on the home page.
The search results page will also have a search form same as on the home page. Its fields will be set according to the currently selected criteria.

## Detailed view of the event

A separate page with all event features is created: title, picture of event, dates from/to, and full description.
The title is linked on the home and the search results page. When clicked, it is transferred to the page of a specific event.

## Adding comments to the event

Only logged-in users can add a comment.
A form for adding a comment is placed under the general information about the event.
A comment can be up to 500 characters long.
The code with the form displays all comments added so far, in descending order.

## Signing up for an event

On the event page, an option (button) to sign up is added, but only for logged-in users.
If the current user is already signed in, he/she sees the relevant information instead of the button. 
A list of all currently registered users is placed next to general information about the event.

## My Events

A section for the logged-in user, where he/she will see all the events, both those in which he/she participated and owned.
User can sort events by name and dates, edit, delete events and unsubscribe.

## API for other services - event listing

The API follows REST rules.
The user is able to download a list of all future events, or can additionally filter the events based on a date range.
