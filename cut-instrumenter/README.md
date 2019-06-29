This Maven project is needed to extract the coverage targets for the test generator given the class under test (`cut`) for the subject application (generated by the [poparsing](https://github.com/matteobiagiola/FSE19-submission-material-DIG/tree/master/poparsing) project).

The program takes as input the path to the `cut` and outputs the lines in the source code that the test generation has to cover.
Each output line to cover represents a transition in the respective [navigation graph](https://github.com/matteobiagiola/FSE19-submission-material-DIG/tree/master/graphs). Hence, covering all the lines means achieving transition coverage of the navigation graph.

Lines to cover for the subject systems used in the experiments are already generated. This project is needed whenever a new subject system is considered. `lines_to_cover` is a property of [evosuite](https://github.com/matteobiagiola/FSE19-submission-material-DIG/blob/master/evosuite/client/src/main/java/org/evosuite/Properties.java) which either has to be specified in the command line (`-Dlines_to_cover=<output_of_cut_instrumenter>`) or in the `evosuite.properties` file (for example [here](https://github.com/matteobiagiola/FSE19-submission-material-DIG/blob/master/fse2019/dimeshift/evosuite-files/evosuite.properties) is the `evosuite.properties` file for the `dimeshift` application). Moreover, the `lines_to_cover` property is considered only if the `filter_lines_to_cover` property is `true`. Setting those two properties is mandatory for the correct functioning of DIG, otherwise all lines of the `cut` are considered. Once again, for the subject systems those properties are already properly set.