package code.execution;

import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;

public class CodeCoverageRunner extends BlockJUnit4ClassRunner {

    public CodeCoverageRunner(Class<?> klass) throws InitializationError {
        super(klass);
    }

    @Override
    protected boolean isIgnored(FrameworkMethod child) {
        if(this.shouldIgnore()) {
            return true;
        }
        return false;
    }

    private boolean shouldIgnore() {
        return RetryRule.hasTestSuiteBug();
    }

}
