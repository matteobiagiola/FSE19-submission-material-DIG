#!/bin/bash
stopContainers(){
	echo "Stopping containers"
	local alg=$1
	if [[ $alg == "SUBWEB" ]]; then
	    docker stop petclinicMosa
	    docker rm petclinicMosa
    elif [[ $alg == "DIGS" ]]; then
        docker stop petclinicAdaptiveSequence
	    docker rm petclinicAdaptiveSequence
    elif [[ $alg == "DIGSI" ]]; then
        docker stop petclinicAdaptiveComplete
	    docker rm petclinicAdaptiveComplete
    elif [[ $alg == "ALL" ]]; then
        docker stop petclinicMosa
	    docker rm petclinicMosa
        docker stop petclinicAdaptiveSequence
	    docker rm petclinicAdaptiveSequence
	    docker stop petclinicAdaptiveComplete
	    docker rm petclinicAdaptiveComplete
    else
        echo "Unknown alg: $alg"
        exit 1
	fi
}

if test $# -lt 4 ; then echo 'ARGS: ITERATIONS [num], ALG [SUBWEB|DIGS|DIGSI|ALL], PO [MANUAL|APOGEN], BUDGET [num (seconds)]' ; exit 1 ; fi

ITERATIONS=$1
ALG=$2
PO=$3
BUDGET=$4
COUNTER_INITIALIZE=$5

COUNTER=0

if [[ -n $COUNTER_INITIALIZE ]]; then
	COUNTER=$COUNTER_INITIALIZE
	ITERATIONS=$(expr $COUNTER + $ITERATIONS)
fi

echo "Iterations: " $ITERATIONS

if [[ $PO != "MANUAL" && $PO != "APOGEN" ]]; then
    echo "Unknown PO version: $PO"
    exit 1
fi

if [[ $ALG == "SUBWEB" ]]; then
    ./run-docker.sh -a 3000 -d 3306 -p yes -n petclinicMosa
elif [[ $ALG == "DIGS" ]]; then
    ./run-docker.sh -a 3001 -d 3307 -p yes -n petclinicAdaptiveSequence
elif [[ $ALG == "DIGSI" ]]; then    
    ./run-docker.sh -a 3002 -d 3308 -p yes -n petclinicAdaptiveComplete
elif [[ $ALG == "ALL" ]]; then
    ./run-docker.sh -a 3000 -d 3306 -p yes -n petclinicMosa
    ./run-docker.sh -a 3001 -d 3307 -p yes -n petclinicAdaptiveSequence
    ./run-docker.sh -a 3002 -d 3308 -p yes -n petclinicAdaptiveComplete
else
    echo "Unknown ALG: $ALG"
    exit 1
fi

echo Waiting for application servers to start...
sleep 60
echo Start testing

while [ $COUNTER -lt $ITERATIONS ]
do
	FAIL=0

    if [[ $ALG == "SUBWEB" ]]; then
        if [[ $PO == "APOGEN" ]]; then
            ./runLoop.sh -p petclinicMosa -s mosa -c $COUNTER -a 3000 -d 3306 -b 9515 -t $BUDGET -r true -g true
        elif [[ $PO == "MANUAL" ]]; then
            ./runLoop.sh -p petclinicMosa -s mosa -c $COUNTER -a 3000 -d 3306 -b 9515 -t $BUDGET -r true
        else
            echo "Unknown PO version: $PO"
            exit 1
        fi
    elif [[ $ALG == "DIGS" ]]; then
        if [[ $PO == "APOGEN" ]]; then
            ./runLoop.sh -p petclinicAdaptiveSequence -s adaptiveSequence -c $COUNTER -a 3001 -d 3307 -b 9516 -t $BUDGET -r true -g true
        elif [[ $PO == "MANUAL" ]]; then
            ./runLoop.sh -p petclinicAdaptiveSequence -s adaptiveSequence -c $COUNTER -a 3001 -d 3307 -b 9516 -t $BUDGET -r true
        else
            echo "Unknown PO version: $PO"
            exit 1
        fi
    elif [[ $ALG == "DIGSI" ]]; then
        if [[ $PO == "APOGEN" ]]; then
            ./runLoop.sh -p petclinicAdaptiveComplete -s adaptiveComplete -c $COUNTER -a 3002 -d 3308 -b 9517 -t $BUDGET -r true -g true
        elif [[ $PO == "MANUAL" ]]; then
            ./runLoop.sh -p petclinicAdaptiveComplete -s adaptiveComplete -c $COUNTER -a 3002 -d 3308 -b 9517 -t $BUDGET -r true
        else
            echo "Unknown PO version: $PO"
            exit 1
        fi
    elif [[ $ALG == "ALL" ]]; then
        if [[ $PO == "APOGEN" ]]; then
            ./runLoop.sh -p petclinicMosa -s mosa -c $COUNTER -a 3000 -d 3306 -b 9515 -t 1800 -r true -g true &
	        sleep 60
	        ./runLoop.sh -p petclinicAdaptiveSequence -s adaptiveSequence -c $COUNTER -a 3001 -d 3307 -b 9516 -t $BUDGET -r true -g true &
	        sleep 60
	        ./runLoop.sh -p petclinicAdaptiveComplete -s adaptiveComplete -c $COUNTER -a 3002 -d 3308 -b 9517 -t $BUDGET -r true -g true &
        elif [[ $PO == "MANUAL" ]]; then
            ./runLoop.sh -p petclinicMosa -s mosa -c $COUNTER -a 3000 -d 3306 -b 9515 -t 1800 -r true &
	        sleep 60
	        ./runLoop.sh -p petclinicAdaptiveSequence -s adaptiveSequence -c $COUNTER -a 3001 -d 3307 -b 9516 -t $BUDGET -r true &
	        sleep 60
	        ./runLoop.sh -p petclinicAdaptiveComplete -s adaptiveComplete -c $COUNTER -a 3002 -d 3308 -b 9517 -t $BUDGET -r true &
        else
            echo "Unknown PO version: $PO"
            exit 1
        fi
    else
        echo "Unknown ALG: $ALG"
        exit 1
    fi

	for job in $(jobs -p)
	do
	    echo "PID of running job:" $job
	    wait $job || let "FAIL+=1"
	done

	if [[ $FAIL == "0" ]]; then
	    echo "YAY! No job has failed."
	else
	    echo "FAIL! Number of failing jobs: ($FAIL)"
	fi
	((COUNTER++))
done

stopContainers $ALG
