Page objects for the splittypie application. Scripts to run test generation experiments.

### Run the application

Execute the Bash script to initialize the Docker image containing the web application:

`./run-docker.sh`

Inside the container, start the application server:

`./run-services-docker.sh`

The application shall run at the address:

`http://localhost:4200`

### Admin Credentials

No user is required to use the application (i.e. no login functionality)

### Stop application and remove container
Type `^C` in the terminal and then type `exit` to exit from the container. In order to remove the container type `docker rm $(docker ps -aq)`. The command will remove all stopped containers.
