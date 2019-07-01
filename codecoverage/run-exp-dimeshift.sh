#!/bin/bash

./run-exp.sh dimeshift ~/workspace/test-generation-results/resultsDimeshift/mosa dimeshiftMosa \
	3000 3306 9515 ~/workspace/fse2019/dimeshift 6969

#./run-exp.sh dimeshift ~/workspace/test-generation-results/resultsDimeshift/mosa dimeshiftMosa \
#	3000 3306 9515 ~/workspace/fse2019/dimeshift 6969
#sleep 10
#./run-exp.sh dimeshift ~/workspace/test-generation-results/resultsDimeshift/adaptive-sequence dimeshiftAdaptiveSequence \
#	3001 3307 9516 ~/workspace/fse2019/dimeshift 7000 &
#sleep 10
#./run-exp.sh dimeshift ~/workspace/test-generation-results/resultsDimeshift/adaptive-complete dimeshiftAdaptiveComplete \
#	3002 3308 9517 ~/workspace/fse2019/dimeshift 7001 &

fail=0

for job in $(jobs -p)
do
    echo "PID of running job:" $job
    wait $job || let "fail+=1"
done

if [[ $fail == "0" ]]; then
    echo "YAY! No job has failed."
else
    echo "FAIL! Number of failing jobs: ($fail)"
fi