#!/bin/bash

usage() {
  echo "Run retroboard application testing"
  echo "Valid options:"
  echo " -h prints this message."
  echo " -i install|skip [skip], install changes in evosuite client or skip"
  echo " -s mosa|whole|adaptiveSequence|adaptiveComplete|random [random], strategy with which ES will run"
  echo " -c NUMBER [0], counter suffix of results directory; loop that calls this script should pass it properly"
  echo " -p STRING [retroboard], project name; name of the docker container, session file name, ES logs name"
  echo " -a NUMBER [4000], port of the application server for remote connection"
  echo " -b NUMBER [9515], port of the chromedriver process"
  echo " -r true|false [false], production usage or not. Production true involves LOG level INFO evosuite, browser headless, no debug file in evosuite for troubleshooting selenium problems in test cases"
  echo " -t NUMBER [300s = 5m], search budget"
  echo " -g true|false [false], apogen po or not"
}

SKIP_INSTALL=skip
STRATEGY=random
ITERATIONS=1
COUNTER=0
PROJECT_NAME=retroboard
APP_PORT=4000
CHROMEDRIVER_PORT=9515
PRODUCTION=false
SEARCH_BUDGET=300
APOGEN=false

OS=$(uname)

function checkIfProcessIsNotListeningOnPort(){
  local PORT=$1
  if [[ $OS == "Linux" ]]; then
    if [[ $(ss -nlp | grep -E "tcp(.*)LISTEN(.*):::$PORT") ]]; then
      echo There is a process listening on port: $PORT. This port must be free.
      exit 1
    fi
  elif [[ $OS == "Darwin" ]]; then
    if [[ $(lsof -Pi :$PORT -sTCP:LISTEN) ]]; then
      echo There is a process listening on port: $PORT. This port must be free.
      exit 1
    fi
  else  
    echo Unknown os name: $OS
    exit 1
  fi
}

while getopts 'hi:s:c:p:a:b:r:t:g:' arg
    do
        case ${arg} in
	    h)
		    usage
		    exit 0
		    ;;
        i)
		    SKIP_INSTALL=${OPTARG};;
        s)
		    STRATEGY=${OPTARG};;
	    c)
		    COUNTER=${OPTARG};;
		p)
		    PROJECT_NAME=${OPTARG};;
		a)
		    APP_PORT=${OPTARG};;
		b)
		    CHROMEDRIVER_PORT=${OPTARG};;
		r)
		    PRODUCTION=${OPTARG};;
		t)
		    SEARCH_BUDGET=${OPTARG};;
    g)
        APOGEN=${OPTARG};;
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

echo "Project name: " $PROJECT_NAME
echo "Application port for remote connection: " $APP_PORT
echo "Chromedriver port: " $CHROMEDRIVER_PORT

DRIVER_HEADLESS=false
EVOSUITE_PROPERTIES_FILE=$PWD/evosuite-files/evosuite.properties
if [[ $PRODUCTION == "true" ]]; then
    DRIVER_HEADLESS=true
    if [[ $OS == "Darwin" ]]; then
        sed -i '' 's/debug_test_case=true/debug_test_case=false/g' $EVOSUITE_PROPERTIES_FILE
        sed -i '' 's/log.level=DEBUG/log.level=INFO/g' $EVOSUITE_PROPERTIES_FILE
    elif [[ $OS == "Linux" ]]; then
        sed -i 's/debug_test_case=true/debug_test_case=false/g' $EVOSUITE_PROPERTIES_FILE
        sed -i 's/log.level=DEBUG/log.level=INFO/g' $EVOSUITE_PROPERTIES_FILE
    else
        echo "Unknown OS for running sed command: "$OS
    fi
else
    DRIVER_HEADLESS=false
    if [[ $OS == "Darwin" ]]; then
        sed -i '' 's/debug_test_case=false/debug_test_case=true/g' $EVOSUITE_PROPERTIES_FILE
        sed -i '' 's/log.level=INFO/log.level=DEBUG/g' $EVOSUITE_PROPERTIES_FILE
    elif [[ $OS == "Linux" ]]; then
        sed -i 's/debug_test_case=false/debug_test_case=true/g' $EVOSUITE_PROPERTIES_FILE
        sed -i 's/log.level=INFO/log.level=DEBUG/g' $EVOSUITE_PROPERTIES_FILE
    else
        echo "Unknown OS for running sed command: "$OS
    fi
fi

PROPERTIES_FILE=$PWD/src/main/resources/app.properties
cat >$PROPERTIES_FILE <<EOL
chromedriverPort=${CHROMEDRIVER_PORT}
appPort=${APP_PORT}
driverHeadless=${DRIVER_HEADLESS}
EOL

if [ "$SKIP_INSTALL" == "install" ] ; then
	echo "installing client code changes in evosuite"
	mvn -f ~/workspace/evosuite/client/pom.xml install -DskipTests
elif [ "$SKIP_INSTALL" == "skip" ] ; then
	echo "skipping install"
else
	echo "please insert as first argument 'install' or 'skip' to continue"
	exit
fi


if [ "$STRATEGY" == "mosa" ] ; then
	echo "MOSA STRATEGY"
elif [ "$STRATEGY" == "whole" ] ; then
	echo "WHOLE SUITE STRATEGY"
elif [ "$STRATEGY" == "adaptiveSequence" ] ; then
        echo "ADAPTIVE RANDOM STRATEGY: sequence only"
elif [ "$STRATEGY" == "adaptiveComplete" ] ; then
        echo "ADAPTIVE RANDOM STRATEGY: sequence + input"
elif [ "$STRATEGY" == "random" ] ; then
        echo "RANDOM STRATEGY"
else
	echo "please type 'mosa', 'whole', 'adaptiveSequence', 'adaptiveComplete' or 'random' as second argument to continue"
	exit
fi

checkIfProcessIsNotListeningOnPort $CHROMEDRIVER_PORT

# Start chromedriver: chromedriver bin must be in the path
echo "Starting chromedriver on port "$CHROMEDRIVER_PORT
chromedriver --port=$CHROMEDRIVER_PORT &

SUFFIX=$PROJECT_NAME"_"$COUNTER
TEST_DIR=$HOME/Desktop/test$SUFFIX
if [ "$STRATEGY" == "mosa" ] ; then
  if [ "$APOGEN" == "true" ] ; then
    ./run.sh -class main.ClassUnderTestApogen -generateMOSuite -Dtest_dir=$TEST_DIR \
        -Dgraph_path=workspace/graphs/retroboard-apogen.txt -Dstart_node=LoginPage \
        -Dlines_to_cover=28:42:56:72:92:107:123:144:162:176:190:205:223:237:252:270:291:294:311:332:353:375:397:419:437:452 \
        -Dreport_dir=$TEST_DIR/evosuite-report -Dsearch_budget=$SEARCH_BUDGET -criterion line \
        > ~/Desktop/logs$SUFFIX.txt 2> ~/Desktop/errors$SUFFIX.txt
  else
    ./run.sh -class main.ClassUnderTest -generateMOSuite -Dtest_dir=$TEST_DIR \
      -Dreport_dir=$TEST_DIR/evosuite-report -Dsearch_budget=$SEARCH_BUDGET -criterion line \
      > ~/Desktop/logs$SUFFIX.txt 2> ~/Desktop/errors$SUFFIX.txt
  fi
elif [ "$STRATEGY" == "whole" ] ; then
  ./run.sh -class main.ClassUnderTest -Dtest_dir=$TEST_DIR -Dreport_dir=$TEST_DIR/evosuite-report \
    -Dsearch_budget=$SEARCH_BUDGET -criterion line \
    > ~/Desktop/logs$SUFFIX.txt 2> ~/Desktop/errors$SUFFIX.txt
elif [ "$STRATEGY" == "adaptiveSequence" ] ; then
  if [ "$APOGEN" == "true" ] ; then
    ./run.sh -class main.ClassUnderTestApogen -generateAdaptiveRandom -Dtest_dir=$TEST_DIR \
        -Dgraph_path=workspace/graphs/retroboard-apogen.txt -Dstart_node=LoginPage \
        -Dlines_to_cover=28:42:56:72:92:107:123:144:162:176:190:205:223:237:252:270:291:294:311:332:353:375:397:419:437:452 \
        -Dreport_dir=$TEST_DIR/evosuite-report -Dsearch_budget=$SEARCH_BUDGET -criterion line \
        > ~/Desktop/logs$SUFFIX.txt 2> ~/Desktop/errors$SUFFIX.txt
  else
    ./run.sh -class main.ClassUnderTest -generateAdaptiveRandom -Dtest_dir=$TEST_DIR \
      -Dreport_dir=$TEST_DIR/evosuite-report -Dsearch_budget=$SEARCH_BUDGET -criterion line \
      -Dinput_distance=false > ~/Desktop/logs$SUFFIX.txt 2> ~/Desktop/errors$SUFFIX.txt
  fi
elif [ "$STRATEGY" == "adaptiveComplete" ] ; then
  if [ "$APOGEN" == "true" ] ; then
    ./run.sh -class main.ClassUnderTestApogen -generateAdaptiveRandom -Dtest_dir=$TEST_DIR \
        -Dgraph_path=workspace/graphs/retroboard-apogen.txt -Dstart_node=LoginPage \
        -Dlines_to_cover=28:42:56:72:92:107:123:144:162:176:190:205:223:237:252:270:291:294:311:332:353:375:397:419:437:452 \
        -Dreport_dir=$TEST_DIR/evosuite-report -Dsearch_budget=$SEARCH_BUDGET -criterion line -Dinput_distance=true \
        > ~/Desktop/logs$SUFFIX.txt 2> ~/Desktop/errors$SUFFIX.txt
  else
    ./run.sh -class main.ClassUnderTest -generateAdaptiveRandom -Dtest_dir=$TEST_DIR \
      -Dreport_dir=$TEST_DIR/evosuite-report -Dsearch_budget=$SEARCH_BUDGET -criterion line \
      -Dinput_distance=true > ~/Desktop/logs$SUFFIX.txt 2> ~/Desktop/errors$SUFFIX.txt
  fi
elif [ "$STRATEGY" == "random" ] ; then
  ./run.sh -class main.ClassUnderTest -generateCustomRandom -Dtest_dir=$TEST_DIR \
    -Dreport_dir=$TEST_DIR/evosuite-report -Dsearch_budget=$SEARCH_BUDGET -criterion line \
    > ~/Desktop/logs$SUFFIX.txt 2> ~/Desktop/errors$SUFFIX.txt
fi
sleep 2
mv $HOME/Desktop/logs$SUFFIX.txt $HOME/Desktop/test$SUFFIX/logs$SUFFIX.txt
mv $HOME/Desktop/errors$SUFFIX.txt $HOME/Desktop/test$SUFFIX/errors$SUFFIX.txt
sleep 2

echo "Stopping chromedriver process listening on port "$CHROMEDRIVER_PORT

PID_CHROMEDRIVER_TO_KILL=$(lsof -Pan -i | grep "chromedri" | grep "127.0.0.1:"$CHROMEDRIVER_PORT | awk '{print $2}')
if [ -z "$PID_CHROMEDRIVER_TO_KILL" ]; then
  echo Error in killing chromedriver process. PID of chromedriver is empty: $PID_CHROMEDRIVER_TO_KILL
  echo "Removing session file if exists"
  if [[ -e $HOME/Desktop/$PROJECT_NAME.ser ]]; then
      rm $HOME/Desktop/$PROJECT_NAME.ser
  fi
  exit 1
fi

echo "Finding children processes of chromedriver and killing them"
pgrep -P $PID_CHROMEDRIVER_TO_KILL | xargs kill -9
kill -9 $PID_CHROMEDRIVER_TO_KILL

echo "Removing session file if exists"
if [[ -e $HOME/Desktop/$PROJECT_NAME.ser ]]; then
    rm $HOME/Desktop/$PROJECT_NAME.ser
fi



