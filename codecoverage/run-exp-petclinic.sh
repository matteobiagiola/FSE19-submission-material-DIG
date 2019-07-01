#!/bin/bash

./run-exp.sh petclinic ~/workspace/results/resultsPetclinic/mosa petclinicMosa \
	3006 3312 9521 ~/workspace/fse2019/petclinic 7005

#./run-exp.sh petclinic ~/workspace/results/resultsPetclinic/mosa petclinicMosa \
#	3006 3312 9521 ~/workspace/fse2019/petclinic 7005 &
#sleep 10
#./run-exp.sh petclinic ~/workspace/results/resultsPetclinic/adaptive-sequence petclinicAdaptiveSequence \
#	3007 3313 9522 ~/workspace/fse2019/petclinic 7006 &
#sleep 10
#./run-exp.sh petclinic ~/workspace/results/resultsPetclinic/adaptive-complete petclinicAdaptiveComplete \
#	3008 3314 9523 ~/workspace/fse2019/petclinic 7007 &

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