#!/bin/bash
stopContainers(){
	echo "Stopping containers"
	local alg=$1
	if [[ $alg == "SUBWEB" ]]; then
	    docker stop phoenixMosa
	    docker rm phoenixMosa
    elif [[ $alg == "DIGS" ]]; then
        docker stop phoenixAdaptiveSequence
	    docker rm phoenixAdaptiveSequence
    elif [[ $alg == "DIGSI" ]]; then
        docker stop phoenixAdaptiveComplete
	    docker rm phoenixAdaptiveComplete
    elif [[ $alg == "ALL" ]]; then
        docker stop phoenixMosa
	    docker rm phoenixMosa
        docker stop phoenixAdaptiveSequence
	    docker rm phoenixAdaptiveSequence
	    docker stop phoenixAdaptiveComplete
	    docker rm phoenixAdaptiveComplete
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
    ./run-docker.sh -a 4000 -d 5432 -p yes -n phoenixMosa
elif [[ $ALG == "DIGS" ]]; then
    ./run-docker.sh -a 4001 -d 5433 -p yes -n phoenixAdaptiveSequence
elif [[ $ALG == "DIGSI" ]]; then    
    ./run-docker.sh -a 4002 -d 5434 -p yes -n phoenixAdaptiveComplete
elif [[ $ALG == "ALL" ]]; then
    ./run-docker.sh -a 4000 -d 5432 -p yes -n phoenixMosa
    ./run-docker.sh -a 4001 -d 5433 -p yes -n phoenixAdaptiveSequence
    ./run-docker.sh -a 4002 -d 5434 -p yes -n phoenixAdaptiveComplete
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
            ./runLoop.sh -p phoenixMosa -s mosa -c $COUNTER -a 4000 -d 5432 -b 9515 -t $BUDGET -r true -g true
        elif [[ $PO == "MANUAL" ]]; then
            ./runLoop.sh -p phoenixMosa -s mosa -c $COUNTER -a 4000 -d 5432 -b 9515 -t $BUDGET -r true
        else
            echo "Unknown PO version: $PO"
            exit 1
        fi
    elif [[ $ALG == "DIGS" ]]; then
        if [[ $PO == "APOGEN" ]]; then
            ./runLoop.sh -p phoenixAdaptiveSequence -s adaptiveSequence -c $COUNTER -a 4001 -d 5433 -b 9516 -t $BUDGET -r true -g true
        elif [[ $PO == "MANUAL" ]]; then
            ./runLoop.sh -p phoenixAdaptiveSequence -s adaptiveSequence -c $COUNTER -a 4001 -d 5433 -b 9516 -t $BUDGET -r true
        else
            echo "Unknown PO version: $PO"
            exit 1
        fi
    elif [[ $ALG == "DIGSI" ]]; then
        if [[ $PO == "APOGEN" ]]; then
            ./runLoop.sh -p phoenixAdaptiveComplete -s adaptiveComplete -c $COUNTER -a 4002 -d 5434 -b 9517 -t $BUDGET -r true -g true
        elif [[ $PO == "MANUAL" ]]; then
            ./runLoop.sh -p phoenixAdaptiveComplete -s adaptiveComplete -c $COUNTER -a 4002 -d 5434 -b 9517 -t $BUDGET -r true
        else
            echo "Unknown PO version: $PO"
            exit 1
        fi
    elif [[ $ALG == "ALL" ]]; then
        if [[ $PO == "APOGEN" ]]; then
            ./runLoop.sh -p phoenixMosa -s mosa -c $COUNTER -a 4000 -d 5432 -b 9515 -t 1800 -r true -g true &
	        sleep 60
	        ./runLoop.sh -p phoenixAdaptiveSequence -s adaptiveSequence -c $COUNTER -a 4001 -d 5433 -b 9516 -t $BUDGET -r true -g true &
	        sleep 60
	        ./runLoop.sh -p phoenixAdaptiveComplete -s adaptiveComplete -c $COUNTER -a 4002 -d 5434 -b 9517 -t $BUDGET -r true -g true &
        elif [[ $PO == "MANUAL" ]]; then
            ./runLoop.sh -p phoenixMosa -s mosa -c $COUNTER -a 4000 -d 5432 -b 9515 -t 1800 -r true &
	        sleep 60
	        ./runLoop.sh -p phoenixAdaptiveSequence -s adaptiveSequence -c $COUNTER -a 4001 -d 5433 -b 9516 -t $BUDGET -r true &
	        sleep 60
	        ./runLoop.sh -p phoenixAdaptiveComplete -s adaptiveComplete -c $COUNTER -a 4002 -d 5434 -b 9517 -t $BUDGET -r true &
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
