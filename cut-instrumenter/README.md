This Maven project is needed to extract the coverage targets for the test generator given the class under test (`cut`) for the subject application (generated by the [poparsing](https://github.com/matteobiagiola/FSE19-submission-material-DIG/tree/master/poparsing) project).

The program takes as input the path to the `cut` and outputs the lines in the source code that the test generation has to cover.
Each output line to cover represents a transition in the respective [navigation graph](https://github.com/matteobiagiola/FSE19-submission-material-DIG/tree/master/graphs). Hence, covering all the lines means achieving transition coverage of the navigation graph.

Lines to cover for the subject systems used in the experiments are already generated. This project is needed whenever a new subject system is considered. `lines_to_cover` is a property of [evosuite](https://github.com/matteobiagiola/FSE19-submission-material-DIG/blob/master/evosuite/client/src/main/java/org/evosuite/Properties.java) which either has to be specified in the command line (`-Dlines_to_cover=<output_of_cut_instrumenter>`) or in the `evosuite.properties` file (for example [here](https://github.com/matteobiagiola/FSE19-submission-material-DIG/blob/master/fse2019/dimeshift/evosuite-files/evosuite.properties) is the `evosuite.properties` file for the `dimeshift` application). Moreover, the `lines_to_cover` property is considered only if the `filter_lines_to_cover` property is `true`. Setting those two properties is mandatory for the correct functioning of DIG, otherwise all lines of the `cut` are considered. Once again, for the subject systems those properties are already properly set.

# Run the project

Run the script `run.sh`. It takes two arguments:
- `project` which is the project name: `dimeshift|splittypie|phoenix|pagekit|petclinic|retroboard`
- `po` which is either `manual` or `apogen`. This last option is needed to distinguish the two `cut` to be parsed in the project directory

Example:

`./run.sh dimeshift apogen`

output:

`Lines to cover: 35 - 41:60:74:92:107:126:144:158:174:194:208:222:236:250:263:278:296:314:333:347:362:376:390:404:423:437:451:469:492:510:525:543:562:585:606`

The two properties in the `evosuite.properties` file should be:

`filter_lines_to_cover=true`

`lines_to_cover=41:60:74:92:107:126:144:158:174:194:208:222:236:250:263:278:296:314:333:347:362:376:390:404:423:437:451:469:492:510:525:543:562:585:606`
