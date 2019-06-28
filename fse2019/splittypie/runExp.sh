#!/bin/bash
stopContainers(){
	echo "Stopping containers"
	local alg=$1
	if [[ $alg == "SUBWEB" ]]; then
	    docker stop splittypieMosa
	    docker rm splittypieMosa
    elif [[ $alg == "DIGS" ]]; then
        docker stop splittypieAdaptiveSequence
	    docker rm splittypieAdaptiveSequence
    elif [[ $alg == "DIGSI" ]]; then
        docker stop splittypieAdaptiveComplete
	    docker rm splittypieAdaptiveComplete
    elif [[ $alg == "ALL" ]]; then
        docker stop splittypieMosa
	    docker rm splittypieMosa
        docker stop splittypieAdaptiveSequence
	    docker rm splittypieAdaptiveSequence
	    docker stop splittypieAdaptiveComplete
	    docker rm splittypieAdaptiveComplete
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
    ./run-docker.sh -a 4200  -p yes -n splittypieMosa
elif [[ $ALG == "DIGS" ]]; then
    ./run-docker.sh -a 4201  -p yes -n splittypieAdaptiveSequence
elif [[ $ALG == "DIGSI" ]]; then    
    ./run-docker.sh -a 4202  -p yes -n splittypieAdaptiveComplete
elif [[ $ALG == "ALL" ]]; then
    ./run-docker.sh -a 4200  -p yes -n splittypieMosa
    ./run-docker.sh -a 4201  -p yes -n splittypieAdaptiveSequence
    ./run-docker.sh -a 4202  -p yes -n splittypieAdaptiveComplete
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
            ./runLoop.sh -p splittypieMosa -s mosa -c $COUNTER -a 4200  -b 9515 -t $BUDGET -r true -g true
        elif [[ $PO == "MANUAL" ]]; then
            ./runLoop.sh -p splittypieMosa -s mosa -c $COUNTER -a 4200  -b 9515 -t $BUDGET -r true
        else
            echo "Unknown PO version: $PO"
            exit 1
        fi
    elif [[ $ALG == "DIGS" ]]; then
        if [[ $PO == "APOGEN" ]]; then
            ./runLoop.sh -p splittypieAdaptiveSequence -s adaptiveSequence -c $COUNTER -a 4201  -b 9516 -t $BUDGET -r true -g true
        elif [[ $PO == "MANUAL" ]]; then
            ./runLoop.sh -p splittypieAdaptiveSequence -s adaptiveSequence -c $COUNTER -a 4201  -b 9516 -t $BUDGET -r true
        else
            echo "Unknown PO version: $PO"
            exit 1
        fi
    elif [[ $ALG == "DIGSI" ]]; then
        if [[ $PO == "APOGEN" ]]; then
            ./runLoop.sh -p splittypieAdaptiveComplete -s adaptiveComplete -c $COUNTER -a 4202  -b 9517 -t $BUDGET -r true -g true
        elif [[ $PO == "MANUAL" ]]; then
            ./runLoop.sh -p splittypieAdaptiveComplete -s adaptiveComplete -c $COUNTER -a 4202  -b 9517 -t $BUDGET -r true
        else
            echo "Unknown PO version: $PO"
            exit 1
        fi
    elif [[ $ALG == "ALL" ]]; then
        if [[ $PO == "APOGEN" ]]; then
            ./runLoop.sh -p splittypieMosa -s mosa -c $COUNTER -a 4200  -b 9515 -t 1800 -r true -g true &
	        sleep 60
	        ./runLoop.sh -p splittypieAdaptiveSequence -s adaptiveSequence -c $COUNTER -a 4201  -b 9516 -t $BUDGET -r true -g true &
	        sleep 60
	        ./runLoop.sh -p splittypieAdaptiveComplete -s adaptiveComplete -c $COUNTER -a 4202  -b 9517 -t $BUDGET -r true -g true &
        elif [[ $PO == "MANUAL" ]]; then
            ./runLoop.sh -p splittypieMosa -s mosa -c $COUNTER -a 4200  -b 9515 -t 1800 -r true &
	        sleep 60
	        ./runLoop.sh -p splittypieAdaptiveSequence -s adaptiveSequence -c $COUNTER -a 4201  -b 9516 -t $BUDGET -r true &
	        sleep 60
	        ./runLoop.sh -p splittypieAdaptiveComplete -s adaptiveComplete -c $COUNTER -a 4202  -b 9517 -t $BUDGET -r true &
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
