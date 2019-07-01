#!/bin/bash

./run-exp.sh retroboard ~/workspace/results/resultsRetroboard/mosa retroboardMosa \
	4003 0 9527 ~/workspace/fse2019/retroboard 7011

#./run-exp.sh retroboard ~/workspace/results/resultsRetroboard/mosa retroboardMosa \
#	4003 0 9527 ~/workspace/fse2019/retroboard 7011 &
#sleep 10
#./run-exp.sh retroboard ~/workspace/results/resultsRetroboard/adaptive-sequence retroboardAdaptiveSequence \
#	4004 0 9528 ~/workspace/fse2019/retroboard 7012 &
#sleep 10
#./run-exp.sh retroboard ~/workspace/results/resultsRetroboard/adaptive-complete retroboardAdaptiveComplete \
#	4005 0 9529 ~/workspace/fse2019/retroboard 7013 &

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