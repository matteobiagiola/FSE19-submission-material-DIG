package code.execution;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class RetryRule implements TestRule {

    private int retryCount;
    private static int sleepConstantAmount = 250;
    private static boolean testSuiteBug = false;

    public RetryRule(int retryCount) {
        this.retryCount = retryCount;
    }

    public Statement apply(Statement base, Description description) {
        return statement(base, description);
    }

    private Statement statement(final Statement base, final Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                Throwable caughtThrowable = null;

                int sleepAmount = 0;
                // implement retry logic here
                for (int i = 0; i < retryCount; i++) {
                    try {
                        sleepAmount = sleepConstantAmount * i;
                        System.setProperty("sleepJUnit", String.valueOf(sleepAmount));
                        System.out.println(description.getMethodName() + ": try run " + (i + 1) + " with sleep timeout " + sleepAmount + ".");
                        base.evaluate();
                        System.out.println(description.getMethodName() + ": run " + (i + 1) + " successful with sleep timeout " + sleepAmount + ".");
                        return;
                    } catch (Throwable t) {
                        caughtThrowable = t;
                        System.err.println(description.getMethodName() + ": run " + (i + 1) + " failed with sleep timeout " + sleepAmount + ".");
                    }
                }
                System.err.println(description.getMethodName() + ": giving up after " + retryCount + " failures.");
                testSuiteBug = true;
                throw caughtThrowable;
            }
        };
    }

    public static boolean hasTestSuiteBug(){
        return testSuiteBug;
    }
}
