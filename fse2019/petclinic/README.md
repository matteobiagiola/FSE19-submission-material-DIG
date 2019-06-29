Page objects for the petclinic application. Scripts to run test case generation experiments.

### Run the application

Execute the Bash script to initialize the Docker image containing the web application:

`./run-docker.sh`

Inside the container, start the Tomcat server and MySQL:

`./run-services-docker.sh`

The application shall run at the address:

`http://localhost:3000`

### Admin Credentials
No user is required to use the application (i.e. no login functionalities)

### Stop application and remove container
Type `^C` in the terminal and then type `exit` to exit from the container. In order to remove the container type `docker rm $(docker ps -aq)`. The command will remove all stopped containers.
