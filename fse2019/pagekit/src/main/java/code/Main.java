package code;

import code.coverage.CodeCoverageState;
import code.main.ApplicationName;
import code.main.CodeCoverage;
import po_utils.MyProperties;

import java.io.File;

public class Main {

    public static void main(String[] args){
        String state;
        String projectPath;
        String pathToESTestSuite;
        String expressServerPortArg;
        int expressServerPort = 6969;
        if(args != null && args.length > 3){
            projectPath = args[0];
            pathToESTestSuite = args[1];
            state = args[2];
            expressServerPortArg = args[3];
            File projectPathFile = new File(projectPath);
            if(!projectPathFile.exists()){
                throw new IllegalArgumentException("Project path " + projectPathFile.getAbsolutePath() + " does not exist");
            }
            if(!projectPathFile.isDirectory()){
                throw new IllegalArgumentException("Project path " + projectPathFile.getAbsolutePath() + " is not a directory");
            }
            File pathToESTestSuiteFile = new File(pathToESTestSuite);
            if(!pathToESTestSuiteFile.exists()){
                throw new IllegalArgumentException("Path to ES test suites " + pathToESTestSuiteFile.getAbsolutePath() + " does not exist");
            }
            if(!pathToESTestSuiteFile.isDirectory()){
                throw new IllegalArgumentException("Path to ES test suites " + pathToESTestSuiteFile.getAbsolutePath() + " is not a directory");
            }
            try{
                expressServerPort = Integer.parseInt(expressServerPortArg);
            }catch (NumberFormatException ex){
                throw new IllegalArgumentException("Express server port must be a number. Found: " + expressServerPortArg);
            }
        }else{
            throw new IllegalArgumentException("No enough arguments passed");
        }
        MyProperties.getInstance().loadProperties(projectPath);
        MyProperties.getInstance().setProjectPath(projectPath);

        String pathToProjectDirectory = projectPath;
        String pathToTestSuiteInProject = pathToProjectDirectory + "/src/main/java/main";
        MyProperties.getInstance().setPathToESTestSuite(pathToESTestSuite);
        CodeCoverageState codeCoverageState;
        if(state != null && !state.isEmpty()){
            codeCoverageState = CodeCoverageState.getMutationState(state);
        }else{
            throw new IllegalStateException("State not defined or empty");
        }
        CodeCoverage codeCoverage = new CodeCoverage(ApplicationName.PAGEKIT,
                codeCoverageState,
                pathToProjectDirectory,
                pathToTestSuiteInProject,
                pathToESTestSuite,
                expressServerPort);
        if(codeCoverageState.equals(CodeCoverageState.MODIFY_TEST_SUITE)){
            codeCoverage.modifyTestSuite();
        }else if(codeCoverageState.equals(CodeCoverageState.CODE_COVERAGE)){
            codeCoverage.runCodeCoverage();
        }
    }
}
