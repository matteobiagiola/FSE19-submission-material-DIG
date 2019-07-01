#!/bin/bash

project_name=$1
test_suites_folder=$2
container_name=$3
project_port_app=$4
project_port_db=$5
chromedriver_port=$6
project_folder=$7
express_server_port=$8

express_server_directory=~/workspace/code-coverage-server/express-istanbul

./run.sh $container_name $test_suites_folder $project_folder $project_name $project_port_app $project_port_db \
	$chromedriver_port $express_server_directory $express_server_port \
	> ~/Desktop/$container_name-code-coverage.txt 2> ~/Desktop/$container_name-code-coverage-errors.txt