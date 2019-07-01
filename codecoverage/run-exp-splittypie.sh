#!/bin/bash

./run-exp.sh splittypie ~/workspace/test-generation-results/resultsSplittypie/mosa splittypieMosa \
	4200 0 9530 ~/workspace/fse2019/splittypie 7014

#./run-exp.sh splittypie ~/workspace/test-generation-results/resultsSplittypie/mosa splittypieMosa \
#	4200 0 9530 ~/workspace/fse2019/splittypie 7014 &
#sleep 10
#./run-exp.sh splittypie ~/workspace/test-generation-results/resultsSplittypie/adaptive-sequence splittypieAdaptiveSequence \
#	4201 0 9531 ~/workspace/fse2019/splittypie 7015 &
#sleep 10
#./run-exp.sh splittypie ~/workspace/test-generation-results/resultsSplittypie/adaptive-complete splittypieAdaptiveComplete \
#	4202 0 9532 ~/workspace/fse2019/splittypie 7016 &

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