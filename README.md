# About
This demo project is a movie search and favorites management system built to demonstrate
the integration of Java with JAX-RS for the backend API and jQuery for the frontend, showcasing
client-side rendering.
It utilizes Hibernate for database operations and cookie-based JWT for authentication 
and authorization.   

The backend includes functionality for an admin user to create user accounts.  
Visitors can search for movies using a third-party API and authenticated users can save them as favorites.

[There is also an Angular version available for the frontend](https://github.com/mixfoodev/user_movies_api_angular_front).


# Setup
### Database
* Create an empty MySQL database.  
* Provide the db credentials in '_**src/main/resources/application_example.properties**_' 
file and rename the file to '_**application.properties**_'.

### Movies API
* Obtain an API key from [www.omdbapi.com](https://www.omdbapi.com/)
* Provide the API Key in '_**src/main/webapp/static/config_example.js**_'
file and rename the file to '_**config.js**_'.

  
Predefined admin credentials:  
username: "admin"  
password: "admin"
