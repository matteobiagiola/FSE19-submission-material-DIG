#!/bin/bash
stopContainers(){
	echo "Stopping containers"
	local alg=$1
	if [[ $alg == "SUBWEB" ]]; then
	    docker stop retroboardMosa
	    docker rm retroboardMosa
    elif [[ $alg == "DIGS" ]]; then
        docker stop retroboardAdaptiveSequence
	    docker rm retroboardAdaptiveSequence
    elif [[ $alg == "DIGSI" ]]; then
        docker stop retroboardAdaptiveComplete
	    docker rm retroboardAdaptiveComplete
    elif [[ $alg == "ALL" ]]; then
        docker stop retroboardMosa
	    docker rm retroboardMosa
        docker stop retroboardAdaptiveSequence
	    docker rm retroboardAdaptiveSequence
	    docker stop retroboardAdaptiveComplete
	    docker rm retroboardAdaptiveComplete
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
    ./run-docker.sh -a 4000  -p yes -n retroboardMosa
elif [[ $ALG == "DIGS" ]]; then
    ./run-docker.sh -a 4001  -p yes -n retroboardAdaptiveSequence
elif [[ $ALG == "DIGSI" ]]; then    
    ./run-docker.sh -a 4002  -p yes -n retroboardAdaptiveComplete
elif [[ $ALG == "ALL" ]]; then
    ./run-docker.sh -a 4000  -p yes -n retroboardMosa
    ./run-docker.sh -a 4001  -p yes -n retroboardAdaptiveSequence
    ./run-docker.sh -a 4002  -p yes -n retroboardAdaptiveComplete
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
            ./runLoop.sh -p retroboardMosa -s mosa -c $COUNTER -a 4000  -b 9515 -t $BUDGET -r true -g true
        elif [[ $PO == "MANUAL" ]]; then
            ./runLoop.sh -p retroboardMosa -s mosa -c $COUNTER -a 4000  -b 9515 -t $BUDGET -r true
        else
            echo "Unknown PO version: $PO"
            exit 1
        fi
    elif [[ $ALG == "DIGS" ]]; then
        if [[ $PO == "APOGEN" ]]; then
            ./runLoop.sh -p retroboardAdaptiveSequence -s adaptiveSequence -c $COUNTER -a 4001  -b 9516 -t $BUDGET -r true -g true
        elif [[ $PO == "MANUAL" ]]; then
            ./runLoop.sh -p retroboardAdaptiveSequence -s adaptiveSequence -c $COUNTER -a 4001  -b 9516 -t $BUDGET -r true
        else
            echo "Unknown PO version: $PO"
            exit 1
        fi
    elif [[ $ALG == "DIGSI" ]]; then
        if [[ $PO == "APOGEN" ]]; then
            ./runLoop.sh -p retroboardAdaptiveComplete -s adaptiveComplete -c $COUNTER -a 4002  -b 9517 -t $BUDGET -r true -g true
        elif [[ $PO == "MANUAL" ]]; then
            ./runLoop.sh -p retroboardAdaptiveComplete -s adaptiveComplete -c $COUNTER -a 4002  -b 9517 -t $BUDGET -r true
        else
            echo "Unknown PO version: $PO"
            exit 1
        fi
    elif [[ $ALG == "ALL" ]]; then
        if [[ $PO == "APOGEN" ]]; then
            ./runLoop.sh -p retroboardMosa -s mosa -c $COUNTER -a 4000  -b 9515 -t 1800 -r true -g true &
	        sleep 60
	        ./runLoop.sh -p retroboardAdaptiveSequence -s adaptiveSequence -c $COUNTER -a 4001  -b 9516 -t $BUDGET -r true -g true &
	        sleep 60
	        ./runLoop.sh -p retroboardAdaptiveComplete -s adaptiveComplete -c $COUNTER -a 4002  -b 9517 -t $BUDGET -r true -g true &
        elif [[ $PO == "MANUAL" ]]; then
            ./runLoop.sh -p retroboardMosa -s mosa -c $COUNTER -a 4000  -b 9515 -t 1800 -r true &
	        sleep 60
	        ./runLoop.sh -p retroboardAdaptiveSequence -s adaptiveSequence -c $COUNTER -a 4001  -b 9516 -t $BUDGET -r true &
	        sleep 60
	        ./runLoop.sh -p retroboardAdaptiveComplete -s adaptiveComplete -c $COUNTER -a 4002  -b 9517 -t $BUDGET -r true &
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
