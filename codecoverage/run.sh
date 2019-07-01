#!/bin/bash

os=$(uname)

function checkFolderExistence(){
	local folder=$1
	if [ ! -d $folder ]; then
    	echo Folder not found: $folder
    	exit 1
	fi
}

function checkIfProcessIsListeningOnPort(){
	local port=$1
	if [[ $os == "Linux" ]]; then
		if [[ ! $(ss -nlp | grep -E "tcp(.*)LISTEN(.*):::$port") ]]; then
			echo There is no process listening on port: $port
			exit 1
		fi
	elif [[ $os == "Darwin" ]]; then
		if [[ ! $(lsof -Pi :$port -sTCP:LISTEN) ]]; then
			echo There is no process listening on port: $port
			exit 1
		fi	
	else
		echo Unknown os name: $os
		exit 1
	fi
}

function checkIfProcessIsNotListeningOnPort(){
	local port=$1
	if [[ $os == "Linux" ]]; then
		if [[ $(ss -nlp | grep -E "tcp(.*)LISTEN(.*):::$port") ]]; then
			echo There is a process listening on port: $port. This port must be free.
			exit 1
		fi
	elif [[ $os == "Darwin" ]]; then
		if [[ $(lsof -Pi :$port -sTCP:LISTEN) ]]; then
			echo There is a process listening on port: $port. This port must be free.
			exit 1
		fi
	else	
		echo Unknown os name: $os
		exit 1
	fi
}

function checkProjectName(){
	local project_name=$1
	if [[ $project_name != "dimeshift" && $project_name != "phoenix" && $project_name != "pagekit" \
		&& $project_name != "splittypie" && $project_name != "retroboard" && $project_name != "petclinic" ]]; then
		echo Unknown project name: $project_name
		exit 1
	fi
}

function isProjectWithDB(){
	local project_name=$1
	checkProjectName $project_name
	if [[ $project_name == "dimeshift" || $project_name == "phoenix" || $project_name == "pagekit" || $project_name == "petclinic" ]]; then
		echo "true"
	else
		echo "false"
	fi
}

function inputsValidation(){
	local container_name=$1
	local test_suites_folder=$2
	local project_folder=$3
	local project_name=$4
	local project_port_app=$5
	local project_port_db=$6
	local chromedriver_port=$7
	local express_server_directory=$8
	local express_server_port=$9

	# check if container exists
	current_container_names=$(docker ps --format "{{.Names}}")
	found='false'
	for current_container_name in $current_container_names; do
	if [[ $current_container_name == $container_name ]]; then
		found='true'
		fi
	done
	if [[ $found == 'true' ]]; then
		echo Container with name $container_name already exists. Remove it and re-run the script.
		exit 1
	fi

	checkFolderExistence $test_suites_folder
	checkFolderExistence $project_folder
	checkFolderExistence $express_server_directory

	checkProjectName $project_name

	checkIfProcessIsNotListeningOnPort $project_port_app
	local project_with_db=$(isProjectWithDB $project_name)
	if [[ $project_with_db == "true" ]]; then
		checkIfProcessIsNotListeningOnPort $project_port_db
	fi
	checkIfProcessIsNotListeningOnPort $chromedriver_port
	checkIfProcessIsNotListeningOnPort $express_server_port
}

function stopContainer(){
	local container_name=$1
	echo Stopping container $container
	docker stop $container_name
	docker rm $container_name
}

function killChromedriver(){
	local chromedriver_port=$1

	echo Stopping chromedriver process listening on port $chromedriver_port
	local pid_chromedriver_to_kill=$(lsof -Pan -i | grep "chromedri" | grep "127.0.0.1:"$chromedriver_port | awk '{print $2}')
	if [ -z "$pid_chromedriver_to_kill" ]; then
	  echo Error in killing chromedriver process. PID of chromedriver is empty: $pid_chromedriver_to_kill
	  exit 1
	fi

	echo Finding children processes of chromedriver and killing them
	pgrep -P $pid_chromedriver_to_kill | xargs kill -9
	kill -9 $pid_chromedriver_to_kill

}


function killExpressServer(){
	local express_server_port=$1
	echo Stopping express server listening on port $express_server_port
	local pid_express_server_to_kill=$(lsof -Pan -i | grep "node" | grep "*:"$express_server_port | awk '{print $2}')
	if [ -z "$pid_express_server_to_kill" ]; then
	  echo Error in killing express process. PID of express server is empty: $pid_express_server_to_kill
	  exit 1
	fi

	echo Finding children processes of express and killing them
	pgrep -P $pid_express_server_to_kill | xargs kill -9
	kill -9 $pid_express_server_to_kill
}

function cleanUp(){
	local container_name=$1
	local chromedriver_port=$2
	local express_server_port=$3
	stopContainer $container_name
	killChromedriver $chromedriver_port
	killExpressServer $express_server_port
}

function runContainer(){
    local project_folder=$1
    local project_with_db=$(isProjectWithDB $project_name)
    local project_port_app=$2
    local project_port_db=$3
    local container_name=$4
    if [[ -e $project_folder ]]; then
        if [[ -d $project_folder ]]; then
            if [[ -f $project_folder/run-docker.sh ]]; then
                local pwd=$(pwd)
                cd $project_folder
                if [[ $project_with_db == "true" ]]; then
                    ./run-docker.sh -a $project_port_app -d $project_port_db -p yes -n $container_name -z yes
                else
                    ./run-docker.sh -a $project_port_app -p yes -n $container_name -z yes
                fi
                cd $pwd
            else
                echo $project_folder/run-docker.sh does not exist
                exit 1
            fi
        else
            echo $project_folder is not a directory
            exit 1
        fi
    else
        echo $project_folder path does not exists
        exit 1
    fi
}

function scanTestSuitesFolder(){
	local current_directory=$(pwd)
	local test_suites_folder=$1
	local project_folder=$2
	local project_name=$3
	local project_port_app=$4
	local project_port_db=$5
	local production=$6
	local session_file_name=$7
	local express_server_port=$8

	local test_suite_counter=0
	for i in $( ls $test_suites_folder ); do
		local dir=$test_suites_folder/$i
		echo "* Current directory: " $dir
		cp $dir/main/* $project_folder/src/main/java/main/
		local start_time=$(date +%s)
		./run-test-suite.sh $project_name $session_file_name $project_port_db $project_port_app \
			$chromedriver_port $test_suites_folder/$i $project_folder $production $express_server_port $test_suite_counter
		local end_time=$(date +%s)
		local total_time_in_seconds=$(($end_time - $start_time))
		echo "Total time to run code coverage for test suite: \
		$(($total_time_in_seconds / 3600)) hours, $(($total_time_in_seconds / 60 % 60 )) minutes and $(($total_time_in_seconds % 60)) seconds elapsed."
		cd $project_folder/src/main/java/main
		rm ClassUnderTest*_ESTest_scaffolding.java
		rm ClassUnderTest*_ESTest.java
		cd $current_directory
		test_suite_counter=$(($test_suite_counter+1))
	done
}

function runCodeCoverage(){

	local container_name=$1
	local test_suites_folder=$2
	local project_folder=$3
	local project_name=$4
	local project_port_app=$5
	local project_port_db=$6
	local chromedriver_port=$7
	local express_server_directory=$8
	local express_server_port=$9

	inputsValidation $container_name $test_suites_folder $project_folder $project_name $project_port_app \
		$project_port_db $chromedriver_port $express_server_directory $express_server_port

    runContainer $project_folder $project_port_app $project_port_db $container_name
	echo The script starts the chromedriver process on port $chromedriver_port and \
		 the express server for collecting coverage reports on port $express_server_port
	echo Waiting for application server to start...
	sleep 120

	# Start chromedriver: chromedriver bin must be in the path
	echo "Starting chromedriver on port "$chromedriver_port
	chromedriver --port=$chromedriver_port &

	# Start express server:
	local current_directory=$(pwd)
	echo "Starting express server on port "$express_server_port
	cd $express_server_directory
	node . $express_server_port &
	cd $current_directory

	local production="true"
	local session_file_name=$container_name
	scanTestSuitesFolder $test_suites_folder $project_folder $project_name \
		$project_port_app $project_port_db $production $session_file_name \
		$express_server_port

	cleanUp $container_name $chromedriver_port $express_server_port
}

# ------------------------------------------------------------------------------------------------------------

container_name=$1
test_suites_folder=$2
project_folder=$3
project_name=$4
project_port_app=$5
project_port_db=$6
chromedriver_port=$7
express_server_directory=$8
express_server_port=$9

start_time=$(date +%s)

runCodeCoverage $container_name $test_suites_folder $project_folder $project_name $project_port_app \
	$project_port_db $chromedriver_port $express_server_directory $express_server_port

end_time=$(date +%s)
total_time_in_seconds=$(($end_time - $start_time))
echo "Total time to run code coverage for all test suites: \
$(($total_time_in_seconds / 3600)) hours, $(($total_time_in_seconds / 60 % 60)) minutes and $(($total_time_in_seconds % 60)) seconds elapsed."
