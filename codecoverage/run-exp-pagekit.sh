#!/bin/bash

./run-exp.sh pagekit ~/workspace/results/resultsPagekit/mosa pagekitMosa \
	3003 3309 9518 ~/workspace/fse2019/pagekit 7002

#./run-exp.sh pagekit ~/workspace/results/resultsPagekit/mosa pagekitMosa \
#	3003 3309 9518 ~/workspace/fse2018/pagekit 7002 &
#sleep 10
#./run-exp.sh pagekit ~/workspace/results/resultsPagekit/adaptive-sequence pagekitAdaptiveSequence \
#	3004 3310 9519 ~/workspace/fse2018/pagekit 7003 &
#sleep 10
#./run-exp.sh pagekit ~/workspace/results/resultsPagekit/adaptive-complete pagekitAdaptiveComplete \
#	3005 3311 9520 ~/workspace/fse2018/pagekit 7004 &

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