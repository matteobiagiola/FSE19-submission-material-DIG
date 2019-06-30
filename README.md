# FSE19 replication package
This repository contains the tool implementing the approach described in an ESEC/FSE19 submission, together with the subjects used in the evaluation. However, only the test generator in its variants (diversity-based and search-based) is reported here. The Apogen tool to extract the page objects given a web application is available [here](https://github.com/tsigalko18/apogen).

## 1. Automatic Setup

A virtual machine running Ubuntu server 18.04 is available for download at https://drive.google.com/file/d/1Op2oLQfELkCWGVJ5zrKS5GT1NwqZ96xA/view?usp=sharing. The virtual machine contains this repository and all the dependencies needed to run DIG on the web application subjects. 

The virtual machine was created with VirtualBox and was exported in the `.ova` format, a platform-independent distribution format for virtual machines. It can be imported by any virtualization software although it was tested only on VirtualBox. Instructions on how to import an `.ova` format virtual machine in VirtualBox and VMWare Fusion are listed below:

- VirtualBox: https://www.techjunkie.com/ova-virtualbox/
- VMWare Fusion: https://pubs.vmware.com/fusion-5/index.jsp?topic=%2Fcom.vmware.fusion.help.doc%2FGUID-275EF202-CF74-43BF-A9E9-351488E16030.html

The minimum amount of RAM to assign to the virtual machine is `4GB`.

Login credentials:
- username: `ubuntu`
- password: `fse2019`

If the automatic setup worked, you can skip to [the run experiments section](#2-run-the-experiments-test-case-generation---after-the-setup). Otherwise procede to the [manual setup section](#11-manual-setup).

#### 1.1 Manual Setup

##### 1.1.1 DIG and the test suite subjects have the following dependencies:

1. Java JDK 1.8 (https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
2. Maven 3.6.0 (https://maven.apache.org/download.cgi)
3. Chrome browser 71.0 (november 2018). It is not possible to download that version for the official google repository. [In an unofficial repository](https://www.slimjet.com/chrome/google-chrome-old-version.php) it is possible to download previous versions of chrome, among which the 71.0 
4. chromedriver 2.46.628411 (http://chromedriver.chromium.org/). [This repository](https://github.com/matteobiagiola/FSE19-submission-material-DIG/tree/master/chromedriver-2.46) contains that version for Mac and Linux OSs. Once unzipped the chromedriver zip for your OS, make sure that the chromedriver binary is in the system `PATH` environment variable. In other words, it should be possible to run the command `chromedriver` from any position in your file system
5. selenium webdriver 3.3.1 (https://www.seleniumhq.org/projects/webdriver/). Also in this case the library is available in [this repository](https://github.com/matteobiagiola/FSE19-submission-material-DIG/tree/master/selenium-custom-library). To install the library is sufficient to move the `selenium` directory you find [here](https://github.com/matteobiagiola/FSE19-submission-material-DIG/tree/master/selenium-custom-library) to `~/.m2/org/seleniumhq` (create the directories `org` and `seleniumhq` if they do not exist; `~/.m2` is created by `maven` to store all the java libraries installed)
6. Docker CE (https://docs.docker.com/install/). Make sure you can run `docker` commands without `sudo` (it is sufficient to run `sudo usermod -aG docker ${USER}` after installing Docker CE and then reboot the system)

DIG has been tested in MacOS Mojave 10.14.3 and Ubuntu (18.04 LTS and 16.04 LTS).

##### 1.1.2 Clone repo and download docker images

Before running the experiments (assuming that `~` indicates the path to the home directory in your system): 
- clone the repository in `~/workspace` (create the folder `workspace` if it does not exist): `cd ~/workspace && git clone https://github.com/matteobiagiola/FSE19-submission-material-DIG.git` assuming that the directory `~/workspace` is empty
- install evosuite: `cd ~/workpsace/evosuite && mvn clean install -DskipTests`
- compile each subject: `cd ~/workpsace/fse2019/<application_name> && mvn clean compile` where `<application_name>` is: 
  - `dimeshift|pagekit|splittypie|phoenix|retroboard|petclinic`
- download docker web application images. The instructions to run each web application are in the relative folders (`fse2019/<application_name>`):
  - `docker pull dockercontainervm/dimeshift:latest` ([README](https://github.com/matteobiagiola/FSE19-submission-material-DIG/blob/master/fse2019/dimeshift/README.md))
  - `docker pull dockercontainervm/pagekit:latest` ([README](https://github.com/matteobiagiola/FSE19-submission-material-DIG/blob/master/fse2019/pagekit/README.md))
  - `docker pull dockercontainervm/splittypie:latest` ([README](https://github.com/matteobiagiola/FSE19-submission-material-DIG/blob/master/fse2019/splittypie/README.md))
  - `docker pull dockercontainervm/phoenix-trello:latest` ([README](https://github.com/matteobiagiola/FSE19-submission-material-DIG/blob/master/fse2019/phoenix/README.md))
  - `docker pull dockercontainervm/retroboard:latest` ([README](https://github.com/matteobiagiola/FSE19-submission-material-DIG/blob/master/fse2019/retroboard/README.md))
  - `docker pull dockercontainervm/petclinic:latest` ([README](https://github.com/matteobiagiola/FSE19-submission-material-DIG/blob/master/fse2019/petclinic/README.md))

## 2. Run the experiments (test case generation - after the setup)

In each subject directory there is the `runExp.sh` script which can be used to run test generation experiments. Let us take the `dimeshift` application as example. The run experiments script can be found [here](https://github.com/matteobiagiola/FSE19-submission-material-DIG/blob/master/fse2019/dimeshift/runExp.sh) for the dimeshift application. The script takes four arguments:
- the first argument represents the number of times (iterations) the test generator has to be run. In the experiments carried out in the paper the number of iterations is 15, in order to cope with the randomness of the algorithms used to generate tests
- the second argument is the test generator to be used. Available values are: `SUBWEB|DIGS|DIGSI|ALL`. `SUBWEB` uses the search-based test generator which is described in details [in this publication](https://www.researchgate.net/publication/319138308_Search_Based_Path_and_Input_Data_Generation_for_Web_Application_Testing). `DIGS` and `DIGSI` use the diversity-based test generator, considering only the sequence of methods (`DIGS`) or sequence and input values (`DIGSI`). The `ALL` value means that three experiments will start in parallel, each one with the previous options (respectively `SUBWEB`, `DIGS`, `DIGSI`). Keep in mind that three docker containers will start and three java programs, hence your machine should have the right amount of CPU and RAM
- the third argument is needed to distinguish the `cut` to select in the project that implements the POs for the target application ([dimeshift](https://github.com/matteobiagiola/FSE19-submission-material-DIG/tree/master/fse2019/dimeshift)). Available values are: `MANUAL|APOGEN`. If `APOGEN` is chosen then the `cut` which models the navigation graph obtained with the POs generated by [Apogen](https://github.com/tsigalko18/apogen) will be considered
- the fourth argument is the search budget, in seconds, to grant the test generator

Examples of usage for the `runExp.sh` script are listed below. The following commands assume you are in the `~/workspace/fse2019/dimeshift` folder, assuming that `~` indicates the path to the home directory in your system:
1. `./runExp.sh 1 SUBWEB APOGEN 60` (`SUBWEB` is called `Mosa` in the evosuite code)
2. `./runExp.sh 1 DIGS APOGEN 60` (`DIGS` is called `AdaptiveSequence` in the evosuite code)
3. `./runExp.sh 1 DIGSI APOGEN 60` (`DIGSI` is called `AdaptiveComplete` in the evosuite code)

Each experiment will run for one minute and there is only one iteration for each test generator.

The [runExp.sh](https://github.com/matteobiagiola/FSE19-submission-material-DIG/blob/master/fse2019/dimeshift/runExp.sh) script starts the docker container for the given application and removes it when the test generation ends. When the test generation ends, the script saves a directory on the `~/Desktop` with the name `test<application_name><Mosa|AdaptiveSequence|AdaptiveComplete>_0`, containing the results of the test generation. The results folder contains the logs, a directory called `evosuite-report` which contains the `statistics.csv` file with the coverage details and the `main` directory (it is called `main` because `main` is the package in which the `cut` is placed in the `dimeshift` project) which contains the java file with the generated test cases.

For the `dimeshift` application is listed below the content the `statistics.csv` file obtained by running each test generator on the virtual machine:

- `SUBWEB`: `~/Desktop/testdimeshiftMosa_0/evosuite_report/statistics.csv`
```
LineCoverage,Statements_Executed,Tests_Executed,Fitness_Evaluations,Total_Time
0.71,0,24,23,169003
```

- `DIGS`: `~/Desktop/testdimeshiftAdaptiveSequence_0/evosuite_report/statistics.csv`
```
LineCoverage,Statements_Executed,Tests_Executed,Fitness_Evaluations,Total_Time
0.74,0,15,14,163740
```

- `DIGSI`: `~/Desktop/testdimeshiftAdaptiveComplete_0/evosuite_report/statistics.csv`
```
LineCoverage,Statements_Executed,Tests_Executed,Fitness_Evaluations,Total_Time
0.85,0,19,18,123090
```

Generated tests are independent by construction. Indeed, in each project that implements the POs for the target application the developer needs to implement a `Reset` class that should clean up the state of the application under test after each test case execution. In other words each test should be executed with the same state of the application under test. For the `dimeshift` application the `Reset` class can be found [here](https://github.com/matteobiagiola/FSE19-submission-material-DIG/blob/master/fse2019/dimeshift/src/main/java/po_utils/ResetAppState.java). It implements a `reset` method with no argument that simply resets the `sql` database (which runs inside the specific `docker` container but it is accessible from the host machine, through proper port bindings). If the application under test does not need a reset (closing the browser after each test execution is enough to clean up the state), then the `reset` method does not need to be implemented. 

## 3. Run the experiments (js code coverage - after the setup)

Coming soon
