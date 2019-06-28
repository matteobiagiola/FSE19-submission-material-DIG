#!/bin/bash

usage() {
  echo "Run docker container with pagekit application"
  echo "Valid options:"
  echo " -h, prints this message."
  echo " -a PORT [3000], assigns application server binding port to localhost. Must be a number!!"
  echo " -d PORT [3306], assigns database binding port to localhost. Must be a number!!"
  echo " -p yes|no [no], run docker for production or not"
  echo " -n STRING [default], assigns a name to the running container (mandatory for production usage)"
}

PORT_APP=3000
PORT_DB=3306
PRODUCTION=no
CONTAINER_NAME=default

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
	if [[ $CONTAINER_NAME == "default" ]]; then
		echo "Assigns a name to the running container for production usage!!"
		exit 1
	fi
	docker run -it --workdir=/var/www/html/pagekit --name=$CONTAINER_NAME --expose 80 --expose 3306 -p $PORT_APP:80 -p $PORT_DB:3306 --entrypoint ./run-services-docker.sh -d dockercontainervm/pagekit:latest bash
	#docker exec --workdir=/var/www/html/pagekit -d $CONTAINER_NAME ./run-services-docker.sh
else
	if [[ $CONTAINER_NAME != "default" ]]; then
		docker run -it --workdir=/var/www/html/pagekit --name=$CONTAINER_NAME --expose 80 --expose 3306 -p $PORT_APP:80 -p $PORT_DB:3306 dockercontainervm/pagekit:latest bash
	else
		docker run -it --workdir=/var/www/html/pagekit --expose 80 --expose 3306 -p $PORT_APP:80 -p $PORT_DB:3306 dockercontainervm/pagekit:latest bash
	fi
fi
