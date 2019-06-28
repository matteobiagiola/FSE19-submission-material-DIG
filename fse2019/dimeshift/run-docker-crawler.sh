#!/bin/bash

usage() {
  echo "Run docker container with dimeshift application: crawljax usage"
  echo "Valid options:"
  echo " -h, prints this message."
  echo " -a PORT [3000], assigns application server binding port to localhost. Must be a number!!"
  echo " -d PORT [3306], assigns database binding port to localhost. Must be a number!!"
  echo " -p yes|no [no], run docker for production or not"
  echo " -n STRING [dimeshift-crawler], assigns a name to the running container (mandatory for production usage)"
}

PORT_APP=3000
PORT_DB=3306
PRODUCTION=no
CONTAINER_NAME=dimeshift-crawler

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
	docker run -it --workdir=/home/dimeshift-application --name=$CONTAINER_NAME --expose 8080 --expose 3306 -p $PORT_APP:8080 -p $PORT_DB:3306 -d dockercontainervm/dimeshift:crawler bash
	docker exec --workdir=/home/dimeshift-application -d $CONTAINER_NAME /bin/bash ./run-services-docker.sh
else
	docker run -it --workdir=/home/dimeshift-application --name=$CONTAINER_NAME --rm --expose 8080 --expose 3306 -p $PORT_APP:8080 -p $PORT_DB:3306 dockercontainervm/dimeshift:crawler bash
fi
