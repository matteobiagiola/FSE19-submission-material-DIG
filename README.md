# FSE19 replication package
This repository contains the tool implementing the approach described in an ESEC/FSE19 submission, together with the subjects used in the evaluation. However, only the test generator in its variants (diversity-based and search-based) is reported here. The Apogen tool to extract the page objects given a web application is available [here](https://github.com/tsigalko18/apogen).

## 1. Automatic Setup

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
