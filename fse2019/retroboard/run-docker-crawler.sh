#!/bin/bash

usage() {
  echo "Run docker container with retroboard application to be used by crawljax"
  echo "Valid options:"
  echo " -h, prints this message."
  echo " -a PORT [3003], assigns application server binding port to localhost. Must be a number!!"
  echo " -p yes|no [no], run docker for production or not"
  echo " -n STRING [retroboard-crawler], assigns a name to the running container"
}

PORT_APP=3003
PRODUCTION=no
CONTAINER_NAME=retroboard-crawler

while getopts 'ha:p:n:' arg
    do
        case ${arg} in
	    h)
		usage
		exit 0
		;;
            a)
		PORT_APP=${OPTARG};;
		p)
			PRODUCTION=${OPTARG};;
	    n)
		CONTAINER_NAME=${OPTARG};;
	    \?)
		echo "Invalid option: -$OPTARG. See usage below."
		usage
		exit 1
		;;
	    :)
		echo "Option -$OPTARG requires an argument." >&2
		exit 1
		;;
        esac
done

echo Port app: $PORT_APP
echo Production: $PRODUCTION
echo Container name: $CONTAINER_NAME

if [[ $PRODUCTION == "yes"  ]]; then
	docker run -it --workdir=/home/retro-board --name=$CONTAINER_NAME --expose 8080 -p $PORT_APP:8080 -d dockercontainervm/retroboard:latest bash
	docker exec --workdir=/home/retro-board $CONTAINER_NAME /usr/bin/git checkout feature/crawler
	docker exec --workdir=/home/retro-board -d $CONTAINER_NAME /bin/bash ./run-services-docker.sh
else
	docker run -it --workdir=/home/retro-board --name=$CONTAINER_NAME --expose 8080 -p $PORT_APP:8080 -d dockercontainervm/retroboard:latest bash
	docker exec --workdir=/home/retro-board $CONTAINER_NAME /usr/bin/git checkout feature/crawler
	docker exec -it --workdir=/home/retro-board $CONTAINER_NAME bash
	docker stop $CONTAINER_NAME
	docker rm $CONTAINER_NAME
fi


