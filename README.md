# FSE19 replication package
This repository contains the tool implementing the approach described in an ESEC/FSE19 submission, together with the subjects used in the evaluation. However, only the test generator in its variants (diversity-based and search-based) is reported here. The Apogen tool to extract the page objects given a web application is available [here](https://github.com/tsigalko18/apogen).

## 1. Automatic Setup

#### 1.1 Manual Setup

##### 1.1.1 DIG and the test suite subjects have the following dependencies:

##### 1.1.2 Clone repo and download docker images

Before running the experiments (assuming that `~` indicates the path the home directory in your system): 
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

## 2. Run the experiments (test case generation - after step 1, setup)

## 3. Run the experiments (js code coverage - after step 2, test case generation)

Coming soon
