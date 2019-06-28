#!/bin/bash

usage() {
  echo "Run docker container with pagekit application in order to be used by crawljax"
  echo "Valid options:"
  echo " -h, prints this message."
  echo " -a PORT [3001], assigns application server binding port to localhost. Must be a number!!"
  echo " -d PORT [3307], assigns database binding port to localhost. Must be a number!!"
  echo " -p yes|no [no], run docker for production or not"
  echo " -n STRING [pagekit-crawler], assigns a name to the running container"
}

PORT_APP=3001
PORT_DB=3307
PRODUCTION=no
CONTAINER_NAME=pagekit-crawler

while getopts 'ha:d:p:n:' arg
    do
        case ${arg} in
	    h)
		usage
		exit 0
		;;
            a)
		PORT_APP=${OPTARG};;
            d)
		PORT_DB=${OPTARG};;
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
echo Port db: $PORT_DB
echo Production: $PRODUCTION
echo Container name: $CONTAINER_NAME

if [[ $PRODUCTION == "yes"  ]]; then
	docker run -it --workdir=/var/www/html/pagekit --name=$CONTAINER_NAME --expose 80 --expose 3306 -p $PORT_APP:80 -p $PORT_DB:3306 -d dockercontainervm/pagekit:latest bash
	docker exec --workdir=/var/www/html/pagekit $CONTAINER_NAME /usr/bin/git checkout feature/crawler
	docker exec --workdir=/var/www/html/pagekit -d $CONTAINER_NAME /bin/bash ./run-services-docker.sh
else
	docker run -it --workdir=/var/www/html/pagekit --name=$CONTAINER_NAME --expose 80 --expose 3306 -p $PORT_APP:80 -p $PORT_DB:3306 -d dockercontainervm/pagekit:latest bash
	docker exec --workdir=/var/www/html/pagekit $CONTAINER_NAME /usr/bin/git checkout feature/crawler
	docker exec -it --workdir=/var/www/html/pagekit $CONTAINER_NAME bash
	docker stop $CONTAINER_NAME
	docker rm $CONTAINER_NAME
fi