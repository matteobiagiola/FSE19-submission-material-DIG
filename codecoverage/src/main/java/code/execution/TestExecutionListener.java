package code.execution;

import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

public class TestExecutionListener extends RunListener {

    public TestExecutionListener(){
    }

    public void testRunStarted(Description description) {
        System.out.println("[MutationTesting]: Number of tests to execute - " + description.testCount());
    }

    public void testRunFinished(Result result) {
        System.out.println("[MutationTesting]: Number of tests executed - " + result.getRunCount());
    }

    public void testStarted(Description description) {
        System.out.println("[MutationTesting]: Starting execution of - " + description.getMethodName());
    }

    public void testFinished(Description description) {
        System.out.println("[MutationTesting]: Finished execution of - " + description.getMethodName());
    }

    public void testFailure(Failure failure) {
        System.out.println("[MutationTesting]: Test execution failed - " + failure.getDescription().getMethodName() + "\nStacktrace: " + failure.getTrace());
    }

    public void testIgnored(Description description) {
        System.out.println("[MutationTesting]: Test ignored - " + description.getMethodName());
    }
}
