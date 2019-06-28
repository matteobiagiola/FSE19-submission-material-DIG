#!/bin/bash

usage() {
  echo "Run docker container with phoenix trello application"
  echo "Valid options:"
  echo " -h, prints this message."
  echo " -a PORT [3003], assigns application server binding port to localhost. Must be a number!!"
  echo " -d PORT [5432], assigns database binding port to localhost. Must be a number!!"
  echo " -p yes|no [no], run docker for production or not"
  echo " -n STRING [phoenix-crawler], assigns a name to the running container (mandatory for production usage)"
}

PORT_APP=3004
PORT_DB=5432
PRODUCTION=no
CONTAINER_NAME=phoenix-crawler

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
	docker run -it --workdir=/home/phoenix-trello --name=$CONTAINER_NAME --expose 4000 --expose 5432 -p $PORT_APP:4000 -p $PORT_DB:5432 -d dockercontainervm/phoenix-trello:latest bash
	docker exec --workdir=/home/phoenix-trello $CONTAINER_NAME /usr/bin/git checkout feature/crawler
	docker exec --workdir=/home/phoenix-trello -d --env PATH=/root/.kiex/elixirs/elixir-1.3.1/bin:/root/.kiex/bin:/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin $CONTAINER_NAME /bin/bash ./run-services-docker.sh
else
	docker run -it --workdir=/home/phoenix-trello --name=$CONTAINER_NAME --expose 4000 --expose 5432 -p $PORT_APP:4000 -p $PORT_DB:5432 -d dockercontainervm/phoenix-trello:latest bash
	docker exec --workdir=/home/phoenix-trello $CONTAINER_NAME /usr/bin/git checkout feature/crawler
	docker exec -it --workdir=/home/phoenix-trello $CONTAINER_NAME bash
	docker stop $CONTAINER_NAME
	docker rm $CONTAINER_NAME
fi
