Page objects for the phoenix application. Scripts to run test generation experiments.

### Run the application

Execute the Bash script to initialize the Docker image containing the web application:

`./run-docker.sh`

Inside the container, start the application server and PostgreSQL:

`./run-services-docker.sh`

The application shall run at the address:

`http://localhost:4000`

### Admin Credentials
There is a pre-registered user:

username: `john@phoenix-trello.com`

password: `12345678`

but the sign up functionality is offered, hence new users can be registered.

### Stop application and remove container
Type `^C` in the terminal and then type `exit` to exit from the container. In order to remove the container type `docker rm $(docker ps -aq)`. The command will remove all stopped containers.
