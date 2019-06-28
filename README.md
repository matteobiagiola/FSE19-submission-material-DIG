# FSE19 replication package
This repository contains the tool implementing the approach described in an ESEC/FSE19 submission, together with the subjects used in the evaluation. 

It is also provided a pdf file with [supplementary material](https://github.com/anonymous-fse19-submitter/FSE19-submission-material/blob/master/supplementary-material.pdf). All the results concerning the three research questions we studied in the paper are reported in the pdf file as tables or charts.

## 1. Automatic Setup

A virtual machine running Ubuntu server 16.04 is available for download at https://drive.google.com/file/d/1O94guDkO5EQLtK-jVMH6r_mJpdbc3OEt/view?usp=sharing. The virtual machine contains this repository and all the dependencies needed to run TEDD on the test suite subjects. 

The virtual machine was created with VMWare Fusion and was exported in the `.ova` format, a platform-independent distribution format for virtual machines. It can be imported by any virtualization software although it was tested only on VirtualBox and VMWare Fusion. Instructions on how to import an `.ova` format virtual machine in VirtualBox and VMWare Fusion are listed below:

- VirtualBox: https://www.techjunkie.com/ova-virtualbox/
- VMWare Fusion: https://pubs.vmware.com/fusion-5/index.jsp?topic=%2Fcom.vmware.fusion.help.doc%2FGUID-275EF202-CF74-43BF-A9E9-351488E16030.html

The minimum amount of RAM to assign to the virtual machine is `4GB`.

Login credentials:
- username: `anonymous`
- password: `fse19`

If the automatic setup worked, you can skip to [the validation experiments section](#2-run-the-experiments-validation---after-the-setup). Otherwise procede to the [manual setup section](#11-manual-setup).

#### 1.1 Manual Setup

In case there is any problem with the [automatic setup](#1-automatic-setup), below there are instructions on how to manually configure the environment to run TEDD on the test suite subjects.

##### 1.1.1 TEDD and the test suite subjects have the following dependencies:

1. Java JDK 1.8 (https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
2. Maven 3.5.4 (https://maven.apache.org/download.cgi)
3. Chrome browser (https://www.google.com/intl/it_ALL/chrome/)
4. Firefox browser (https://www.mozilla.org/it/firefox/new/)
5. Docker CE (https://docs.docker.com/install/)
6. Wordnet (http://wordnetcode.princeton.edu/3.0/WordNet-3.0.tar.gz). Uncompress the folder and move it (`WordNet-3.0`) in `Desktop`.

TEDD has been tested in MacOS Mojave 10.14.3 and Ubuntu (18.04 LTS and 16.04 LTS).

##### 1.1.2 Clone repo and download docker images
Before running the experiments (assuming that `~` indicates the path the home directory in your system): 
- clone the repository (`git clone https://github.com/anonymous-fse19-submitter/FSE19-submission-material.git`) in `~/workspace` (create the folder `workspace` if it does not exist)
- `cd ~/workpsace/FSE19-submission-material/tedd && mvn clean compile`
- `cd ~/workpsace/FSE19-submission-material/testsuite-<application_name> && mvn clean compile` where `<application_name>` is: 
  - `claroline|addressbook|ppma|collabtive|mrbs|mantisbt`
- download docker web application images. The instructions to run each web application are in the relative folders (`FSE19-submission-material/testsuite-<application_name>`):
  - `docker pull dockercontainervm/claroline:1.11.10` ([README](https://github.com/anonymous-fse19-submitter/FSE19-submission-material/blob/master/testsuite-claroline/README.md))
  - `docker pull dockercontainervm/addressbook:8.0.0.0` ([README](https://github.com/anonymous-fse19-submitter/FSE19-submission-material/blob/master/testsuite-addressbook/README.md))
  - `docker pull dockercontainervm/ppma:0.6.0` ([README](https://github.com/anonymous-fse19-submitter/FSE19-submission-material/blob/master/testsuite-ppma/README.md))
  - `docker pull dockercontainervm/collabtive:3.1` ([README](https://github.com/anonymous-fse19-submitter/FSE19-submission-material/blob/master/testsuite-collabtive/README.md))
  - `docker pull dockercontainervm/mrbs:1.4.9` ([README](https://github.com/anonymous-fse19-submitter/FSE19-submission-material/blob/master/testsuite-mrbs/README.md))
  - `docker pull dockercontainervm/mantisbt:1.2.0` ([README](https://github.com/anonymous-fse19-submitter/FSE19-submission-material/blob/master/testsuite-mantisbt/README.md))

##### 1.1.3 Setup app.properties

Rename the `app.example.properties` in `app.properties` in folder `~/FSE19-submission-material/tedd/src/main/resources`. Replace all occurences of `/home/anonymous` with `~` directory (in this case you need to expand `~`, for example `/home/your-username`).
  

## 2. Run the experiments (validation - after the setup)

The main script to run the validation experiments is [run-experiment.sh](https://github.com/anonymous-fse19-submitter/FSE19-submission-material/blob/master/tedd/run-experiment.sh). 

The first argument is the `application_name`. The available values are:
- `claroline|addressbook|ppma|collabtive|mrbs|mantisbt`

The second argument is the `main_class` that is going to be executed. The available values are:
- `baseline_complete|tedd`

The third argument is the `mode`, or the desired configuration of the tool. The available values are:
- `baseline_complete|string_analysis|nlp_verb_only_baseline|nlp_verb_only_string|`
`nlp_dobj_baseline|nlp_dobj_string|nlp_noun_matching_baseline|nlp_noun_matching_string`

The possible combinations of those arguments are listed below (as example `application_name=ppma`). The following commands assume you are in the `~/workspace/FSE19-submission-material/tedd` folder, assuming that `~` indicates the path to the home directory in your system:
1. `./run-experiment.sh ppma baseline_complete baseline_complete` (it takes ~1.3 h to run)
2. `./run-experiment.sh ppma tedd string_analysis` (it takes ~52 min to run)
3. `./run-experiment.sh ppma tedd nlp_verb_only_baseline` (it takes ~18 min to run)
4. `./run-experiment.sh ppma tedd nlp_verb_only_string` (it takes ~18 min to run)
5. `./run-experiment.sh ppma tedd nlp_dobj_baseline` (it takes ~33 min to run)
6. `./run-experiment.sh ppma tedd nlp_dobj_string` (it takes ~33 min to run)
7. `./run-experiment.sh ppma tedd nlp_noun_matching_baseline` (it takes ~32 min to run)
8. `./run-experiment.sh ppma tedd nlp_noun_matching_string` (it takes ~32 min to run)

The [run-experiment.sh](https://github.com/anonymous-fse19-submitter/FSE19-submission-material/blob/master/tedd/run-experiment.sh) script starts the docker container for the given application and removes it when the validation ends. When the validation ends, the script saves a directory on the `~/Desktop` with the application name, containing the results of the validation. The results folder contains the logs, the final dependency graph and the intermediate graphs obtained during the validation. 

Suppose that you have run `./run-experiment.sh ppma tedd nlp_verb_only_baseline`. When the validation ends, move `~/Desktop/ppma/results/<date>-tedd` and look for `logs_tedd_ppma.txt`. Then type `sed -n -e '/====/,/====/ p' logs_tedd_ppma.txt`. The command displays two blocks corresponding to validation and disconnected nodes dependency recovery. For each of them, there are some statistics among which the validation time, number of manifest dependencies and so on. An example of output is shown below:

```
==================
Number of tests: 23
Number of dependencies: 91
Number of dependencies added while checking for missing: 0
Number of false dependencies: 45
Number of manifest dependencies: 22
Number of missing dependencies recovered: 0
Executions: 428
Avg executions: 6
Max executions: 2093
Time: 22.3656005859375 min
Avg time: 20.028896s
==================
==================
Number of tests: 23
Number of dependencies: 22
Number of dependencies added while checking for missing: 0
Number of false dependencies: 0
Number of manifest dependencies: 22
Number of missing dependencies recovered: 0
Executions: 0
Avg executions: 0
Max executions: 506
Time: 22.6024169921875 min
Avg time: 20.24097s
==================
```

It is possible to stop the computation by typing `^C` once on the terminal. Typing `^C` once will end the validation and the script takes care of saving the results computed so far and it removes the docker container for the given application (no final dependency graph is saved). If you press `^C` twice the results are not saved and the docker container is not removed. In order to remove it, first you need to stop it `docker stop $(docker ps -aq)` and then remove it `docker rm $(docker ps -aq)` (specifically, the previous commands will not stop and remove only one container but all containers running on the system).

## 3. Run the experiments (parallelization - after the setup)

The repository has a directory called [ready-to-run-parallelization](https://github.com/anonymous-fse19-submitter/FSE19-submission-material/tree/master/ready-to-run-parallelization) that contains the final dependencies graphs for all the possible configurations of TEDD and for the baseline approach.

The script [run-parallelization.sh](https://github.com/anonymous-fse19-submitter/FSE19-submission-material/blob/master/tedd/run-parallelization.sh) extracts and execute all the possible test suites from each dependency graph in the [ready-to-run-parallelization](https://github.com/anonymous-fse19-submitter/FSE19-submission-material/tree/master/ready-to-run-parallelization) folder for each application.

For example you can move to `~/workspace/FSE19-submission-material/tedd` and type `./run-parallelization.sh ppma nlp_verb_only_baseline`. The command will execute the unique test suites only for one configuration, namely `nlp_verb_only_baseline`. The execution takes approximately 6 minutes.

As in the previous script the results are saved in the `~/Desktop` folder. Move to `~/Desktop/ppma/results/<date>-check_final_graph_correctness` and look for `logs_nlp_verb_only_baseline_check_final_graph_correctness_ppma.txt`. Then type `sed -n -e '/====/,/====/ p logs_nlp_verb_only_baseline_check_final_graph_correctness_ppma.txt`. The command displays a block with statistics like worst case and average case speed-up factors and so on. An example of output is shown below:

```
=====================================
Total number of schedules executed: 22 (independent nodes 2). Deps: 22
Executing test suite in its default order...
Execution of test suite in its default order took 62.163 s
Schedule with the highest runtime of 24.452 s: [PasswordManagerAddEntryTest_0, PasswordManagerRemoveTagsTest_9, PasswordManagerRemoveEntryTest_11, PasswordManagerAddTagTest_14, PasswordManagerEditTagTest_15, PasswordManagerAssignTagToEntryTest_17, PasswordManagerAddMultipleEntriesTest_18, PasswordManagerCheckUsedTagsTest_20]
Worst case speed up factor: 2.5422460330443317
Average speed up factor: 5.9288581758906815
Average runtime schedules parallelized: 10.484818181818182 s
Average schedule lengths: 3.5
False negatives: 0
Dependencies removed because of cycle: 0
=====================================
```
