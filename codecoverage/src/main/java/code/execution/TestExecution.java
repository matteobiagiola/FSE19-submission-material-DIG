package code.execution;

import code.main.ApplicationName;
import code.utils.CodeCoverageException;
import code.utils.MyProperties;
import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.AnnotationSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;
import org.junit.runner.JUnitCore;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TestExecution {

    public void modifyTestSuite(String pathToTestSuiteInProject){
        Path sourcePath = new File(pathToTestSuiteInProject).toPath();
        try {
            String esTestClassFileContent = Files.lines(sourcePath).map(String::valueOf).collect(Collectors.joining("\n"));
            JavaClassSource javaClass = Roaster.parse(JavaClassSource.class, esTestClassFileContent);
            if(MyProperties.getInstance().getApplicationName().equals(ApplicationName.DIMESHIFT)){
                this.configureApplication(javaClass, true);
            }else if(MyProperties.getInstance().getApplicationName().equals(ApplicationName.PAGEKIT)){
                this.configureApplication(javaClass, true);
            }else if(MyProperties.getInstance().getApplicationName().equals(ApplicationName.SPLITTYPIE)){
                this.configureApplication(javaClass, false);
            }else if(MyProperties.getInstance().getApplicationName().equals(ApplicationName.PHOENIX)){
                this.configureApplication(javaClass, true);
            }else if(MyProperties.getInstance().getApplicationName().equals(ApplicationName.RETROBOARD)){
                this.configureApplication(javaClass,false);
            }else if(MyProperties.getInstance().getApplicationName().equals(ApplicationName.PETCLINIC)){
                this.configureApplication(javaClass,true);
            }
            else{
                throw new UnsupportedOperationException("[MutationTesting]: modify test suite does not support application "
                        + MyProperties.getInstance().getApplicationName());
            }
            Writer writer = new PrintWriter(pathToTestSuiteInProject);
            writer.write(javaClass.toString());
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void configureApplication(JavaClassSource javaClass, boolean withDB){
        javaClass.addImport("code.coverage.CoverageManager");
        javaClass.addImport("code.coverage.CoverageInfo");
        javaClass.addImport("code.execution.CodeCoverageRunner");
        if(withDB){
            javaClass.addImport("org.junit.BeforeClass");
        }else{
            javaClass.addImport("org.junit.Before");
        }
        javaClass.addImport("org.junit.AfterClass");
        javaClass.addImport("org.junit.After");
        javaClass.addImport("org.junit.Ignore");
        javaClass.addImport("org.junit.Rule");
        javaClass.addImport("org.openqa.selenium.WebDriver");
        javaClass.addImport("po_utils.DriverProvider");
        javaClass.addImport("po_utils.ResetAppState");
        javaClass.addImport("code.execution.RetryRule");
        javaClass.addImport("java.util.List");
        javaClass.addImport("po_utils.MyProperties");
        javaClass.addImport("po_utils.ResetAppState");
        javaClass.setSuperType("");
        javaClass.removeAllAnnotations();
        javaClass.addAnnotation("RunWith").setClassValue(CodeCoverageRunner.class);
        javaClass.addField("public static WebDriver driver");
        javaClass.addField("public static String pathToESTestSuite = MyProperties.getInstance().getPathToESTestSuite();");
        javaClass.addField("@Rule \n public RetryRule retryRule = new RetryRule(3); \n");
        String classUnderTestFileName = javaClass.getName();
        String className = this.getClassUnderTestName(classUnderTestFileName);
        List<MethodSource<JavaClassSource>> methods = javaClass.getMethods();
        for (int i = 0; i < methods.size(); i++) {
            MethodSource<JavaClassSource> method = methods.get(i);
            List<AnnotationSource<JavaClassSource>> methodAnnotations = method.getAnnotations();
            methodAnnotations.forEach(AnnotationSource::removeAllValues);
            method.addThrows("InterruptedException");
            String body = method.getBody();
            String[] split = body.split("\n");
            String firstStatement = split[0];
            if(firstStatement.contains(className)){
                split[0] = className + " " + this.getClassUnderTestVariable(className) + "0 = new " + className + "(driver);";
            }
            List<String> newStatements = new ArrayList<>();
            for (int j = 0; j < split.length; j++) {
                newStatements.add(split[j]);
                // add sleep statement after every test case statement and after cut constructor statement (j = 0)
                if(split[j].contains(this.getClassUnderTestVariable(className) + "0") || j == 0){
                    newStatements.add("Thread.sleep(Integer.valueOf(System.getProperty(\"sleepJUnit\")));");
                }
            }
            String newBody = newStatements.stream().collect(Collectors.joining("\n"));
            method.setBody(newBody);
        }
        if(withDB){
            // one driver for all test cases in the test suite
            javaClass.addMethod("@BeforeClass \n public static void setup() \n{ driver = new DriverProvider().getActiveDriver(); \n }");
        }else{
            // one driver for each test case in the test suite
            javaClass.addMethod("@Before \n public void setup() \n{ driver = new DriverProvider().getActiveDriver(); \n }");
        }
        javaClass.addMethod("@AfterClass \n public static void resetCoverageStats() \n{ CoverageManager coverageManager = new CoverageManager(); \n coverageManager.resetCoverageStats(); \n ResetAppState.quitDriver(driver); \n }");
        if(withDB){
            // don't reset the driver after each test case execution
            javaClass.addMethod("@After \n public void saveIntermediateCoverageReportAndReset() \n" +
                    "{ CoverageManager coverageManager = new CoverageManager(); \n"
                    + "Object coverage = coverageManager.getCoverageObject(driver); \n"
                    + "coverageManager.sendCoverageObjectToExpressServer(coverage); \n"
                    + "String htmlCoverageReport = coverageManager.getCoverageReportFromExpressServer(); \n"
                    + "List<CoverageInfo> coverageInfos = coverageManager.parseHTMLResponse(htmlCoverageReport); \n"
                    + "coverageManager.writeCoverageReport(coverageInfos, pathToESTestSuite); \n }");
        }else{
            // reset the driver after each test case execution
            javaClass.addMethod("@After \n public void saveIntermediateCoverageReportAndReset() \n" +
                    "{ CoverageManager coverageManager = new CoverageManager(); \n"
                    + "Object coverage = coverageManager.getCoverageObject(driver); \n"
                    + "coverageManager.sendCoverageObjectToExpressServer(coverage); \n"
                    + "String htmlCoverageReport = coverageManager.getCoverageReportFromExpressServer(); \n"
                    + "List<CoverageInfo> coverageInfos = coverageManager.parseHTMLResponse(htmlCoverageReport); \n"
                    + "coverageManager.writeCoverageReport(coverageInfos, pathToESTestSuite); \n "
                    + "ResetAppState.reset(driver); \n }");
        }

    }

    public void runTestSuite(String pathToTestSuiteInProject, String pathToProjectDirectory){
        System.out.println("[CodeCoverage]: Running suite - TestSuite");
        JUnitCore core = new JUnitCore();
        try {
            Class testSuiteClass = Class.forName(this.getTestSuiteClassName(pathToTestSuiteInProject, pathToProjectDirectory));
            core.addListener(new TestExecutionListener());
            core.run(testSuiteClass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new CodeCoverageException(e.getMessage());
        }
    }

    private String getTestSuiteClassName(String pathToTestSuiteInProject, String pathToProjectDirectory){
        String relativePathToTestSuiteInProject = pathToTestSuiteInProject.replace(pathToProjectDirectory, "").replace("/src/main/java/","");
        return relativePathToTestSuiteInProject.replace(".java","").replace("/",".");
    }

    private String lowerCaseOfFirstCharacter(String string){
        return string.substring(0, 1).toLowerCase() + string.substring(1);
    }

    private String getClassUnderTestName(String classUnderTestFileName){
        int underscoreIndex = classUnderTestFileName.indexOf("_");
        if(underscoreIndex == -1){
            throw new IllegalStateException("ClassUnderTest name must have _ESTest suffix");
        }
        return classUnderTestFileName.substring(0, underscoreIndex);
    }

    private String getClassUnderTestVariable(String classUnderTestName){
        return this.lowerCaseOfFirstCharacter(classUnderTestName);
    }
}
