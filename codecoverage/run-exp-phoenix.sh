#!/bin/bash

./run-exp.sh phoenix ~/workspace/results/resultsPhoenix/mosa phoenixMosa \
	4000 5432 9524 ~/workspace/fse2019/phoenix 7008

#./run-exp.sh phoenix ~/workspace/results/resultsPhoenix/mosa phoenixMosa \
#	4000 5432 9524 ~/workspace/fse2019/phoenix 7008 &
#sleep 10
#./run-exp.sh phoenix ~/workspace/results/resultsPhoenix/adaptive-sequence phoenixAdaptiveSequence \
#	4001 5433 9525 ~/workspace/fse2019/phoenix 7009 &
#sleep 10
#./run-exp.sh phoenix ~/workspace/results/resultsPhoenix/adaptive-complete phoenixAdaptiveComplete \
#	4002 5434 9526 ~/workspace/fse2019/phoenix 7010 &

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