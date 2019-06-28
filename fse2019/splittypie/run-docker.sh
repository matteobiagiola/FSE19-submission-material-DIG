#!/bin/bash

usage() {
  echo "Run docker container with splittypie application"
  echo "Valid options:"
  echo " -h, prints this message."
  echo " -a PORT [4200], assigns application server binding port to localhost. Must be a number!!"
  echo " -p yes|no [no], run docker for production or not"
  echo " -n STRING [default], assigns a name to the running container (mandatory for production usage)"
}

PORT_APP=4200
PRODUCTION=no
CONTAINER_NAME=default

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
	if [[ $CONTAINER_NAME == "default" ]]; then
		echo "Assigns a name to the running container for production usage!!"
		exit 1
	fi	
	docker run -it --workdir=/home/splittypie --name=$CONTAINER_NAME --expose 4200 -p $PORT_APP:4200 -d --entrypoint ./run-services-docker.sh dockercontainervm/splittypie:latest bash
	# docker exec --workdir=/home/splittypie -d $CONTAINER_NAME /bin/bash ./run-services-docker.sh
else
	if [[ $CONTAINER_NAME != "default" ]]; then
		docker run -it --workdir=/home/splittypie --name=$CONTAINER_NAME --expose 4200 -p $PORT_APP:4200 dockercontainervm/splittypie:latest bash
	else
		docker run -it --workdir=/home/splittypie --expose 4200 -p $PORT_APP:4200 dockercontainervm/splittypie:latest bash		
	fi
fi
