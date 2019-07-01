package code.main;

import code.coverage.CodeCoverageState;
import code.execution.TestExecution;
import code.utils.CheckCondition;
import code.utils.MyProperties;

import java.io.File;

public class CodeCoverage {

    private CodeCoverageState state;
    private String pathToTestSuiteInProject;
    private String pathToProjectDirectory;
    /**
     * @param applicationName according to enum (application name to test)
     * @param state according to enum (code coverage step to execute)
     * @param pathProjectDirectory must be absolute (path to project directory where test suite is located)
     * @param pathToTestSuiteInProject must be absolute (path in the current project where the test suite is located)
     * @param pathToESTestSuite must be absolute (path where ES test suites are placed)
     * */
    public CodeCoverage(ApplicationName applicationName,
                        CodeCoverageState state,
                        String pathProjectDirectory,
                        String pathToTestSuiteInProject,
                        String pathToESTestSuite,
                        int expressServerPort){

        CheckCondition.checkNotNull(state,
                "[CodeCoverage]: state cannot be null");
        CheckCondition.checkNotNull(applicationName,
                "[CodeCoverage]: application name cannot be null");
        CheckCondition.checkNotNull(pathProjectDirectory,
                "[CodeCoverage]: path to project directory cannot be null");
        CheckCondition.checkNotNull(pathToTestSuiteInProject,
                "[CodeCoverage]: path to test suite cannot be null");
        CheckCondition.checkNotNull(pathToESTestSuite,
                "[CodeCoverage]: path to ES test suite cannot be null");
        CheckCondition.checkNotNull(expressServerPort,
                "[CodeCoverage]: express server port cannot be null");

        this.state = state;
        MyProperties.getInstance().setApplicationName(applicationName);
        MyProperties.getInstance().setExpressServerPort(expressServerPort);
        this.pathToProjectDirectory = pathProjectDirectory;
        this.pathToTestSuiteInProject = this.getTestSuitePath(pathToTestSuiteInProject);
        this.setClasspath(pathToProjectDirectory);
    }

    public void modifyTestSuite(){
        CheckCondition.checkState(state.equals(CodeCoverageState.MODIFY_TEST_SUITE),
                "[CodeCoverage]: cannot run this method because" +
                        " application is in the wrong state. Expected: " + CodeCoverageState.MODIFY_TEST_SUITE.value()
                        + ", found: " + state.value());
        CheckCondition.checkArgument(new File(this.pathToTestSuiteInProject).exists(),
                "[CodeCoverage]: test suite in project must exist");

        System.out.println("[CodeCoverage]: modifying evosuite test suite " + this.pathToTestSuiteInProject + "...");
        TestExecution testExecution = new TestExecution();
        testExecution.modifyTestSuite(this.pathToTestSuiteInProject);
        System.out.println("[CodeCoverage]: evosuite test suite " + pathToTestSuiteInProject + " modified successfully!");
    }

    public void runCodeCoverage(){
        CheckCondition.checkState(state.equals(CodeCoverageState.CODE_COVERAGE),
                "[CodeCoverage]: cannot run this method because" +
                        " application is in the wrong state. Expected: " + CodeCoverageState.CODE_COVERAGE.value()
                        + ", found: " + state.value());

        System.out.println("[CodeCoverage]: running code coverage...");

        this.runTestSuite(this.pathToTestSuiteInProject, this.pathToProjectDirectory);
    }

    private void runTestSuite(String pathToTestSuiteInProject, String pathToProjectDirectory){

        TestExecution testExecution = new TestExecution();
        testExecution.runTestSuite(pathToTestSuiteInProject, pathToProjectDirectory);
    }

    private void setClasspath(String pathToProjectDirectory){
        String currentClasspath = MyProperties.getInstance().getClasspath();
        String newClasspath = currentClasspath + ":" + pathToProjectDirectory + "/target/classes";
        MyProperties.getInstance().setClasspath(newClasspath);
    }

    private String getTestSuitePath(String pathToTestSuiteInProject){
        File packageWithTestSuiteInProject = new File(pathToTestSuiteInProject);
        if(!packageWithTestSuiteInProject.exists()){
            throw new IllegalStateException("Path to test suite in project " + pathToTestSuiteInProject + " does not exist.");
        }
        if(!packageWithTestSuiteInProject.isDirectory()){
            throw new IllegalStateException("Path to test suite in project " + pathToTestSuiteInProject + " is not a directory.");
        }
        File[] javaFiles = packageWithTestSuiteInProject.listFiles();
        for (File javaFile: javaFiles){
            if(javaFile.getAbsolutePath().contains("_ESTest.java")){
                return javaFile.getAbsolutePath();
            }
        }
        throw new IllegalStateException("Path to test suite in project " + pathToTestSuiteInProject + " does not contain any evosuite test suite (_ESTest)");
    }
}
