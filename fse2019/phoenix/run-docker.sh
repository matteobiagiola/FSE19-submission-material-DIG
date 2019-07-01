#!/bin/bash

usage() {
  echo "Run docker container with phoenix trello application"
  echo "Valid options:"
  echo " -h, prints this message."
  echo " -a PORT [4000], assigns application server binding port to localhost. Must be a number!!"
  echo " -d PORT [5432], assigns database binding port to localhost. Must be a number!!"
  echo " -p yes|no [no], run docker for production or not"
  echo " -n STRING [default], assigns a name to the running container (mandatory for production usage)"
  echo " -z yes|no, running js code coverage instrumentation"
}

PORT_APP=4000
PORT_DB=5432
PRODUCTION=no
CONTAINER_NAME=default
CODE_COVERAGE_INSTRUMENTATION=no

while getopts 'ha:d:p:n:z:' arg
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
		z)
		CODE_COVERAGE_INSTRUMENTATION=${OPTARG};;
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
	if [[ $CODE_COVERAGE_INSTRUMENTATION == "yes" ]]; then
	    docker run -it --workdir=/home/phoenix-trello --name=$CONTAINER_NAME --expose 4000 --expose 5432 -p $PORT_APP:4000 \
	    -p $PORT_DB:5432 --env PATH=/root/.kiex/elixirs/elixir-1.3.1/bin:/root/.kiex/bin:/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin \
	    -d --entrypoint ./run-code-instrumentation.sh dockercontainervm/phoenix-trello:latest bash
    else
        docker run -it --workdir=/home/phoenix-trello --name=$CONTAINER_NAME --expose 4000 --expose 5432 -p $PORT_APP:4000 \
	    -p $PORT_DB:5432 --env PATH=/root/.kiex/elixirs/elixir-1.3.1/bin:/root/.kiex/bin:/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin \
	    -d --entrypoint ./run-services-docker.sh dockercontainervm/phoenix-trello:latest bash
	fi

else
	if [[ $CONTAINER_NAME != "default" ]]; then
		docker run -it --workdir=/home/phoenix-trello --name=$CONTAINER_NAME --expose 4000 --expose 5432 \
		    -p $PORT_APP:4000 -p $PORT_DB:5432 dockercontainervm/phoenix-trello:latest bash
	else
		docker run -it --workdir=/home/phoenix-trello --expose 4000 --expose 5432 -p $PORT_APP:4000 \
		    -p $PORT_DB:5432 dockercontainervm/phoenix-trello:latest bash
	fi
fi
