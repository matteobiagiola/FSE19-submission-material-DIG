package code.coverage;

public enum CodeCoverageState {

    CODE_COVERAGE("code_coverage"),
    MODIFY_TEST_SUITE ("modify_test_suite");

    private final String mutationState;

    CodeCoverageState(String mutationState){
        this.mutationState = mutationState;
    }

    public String value() {
        return this.mutationState;
    }

    public static CodeCoverageState getMutationState(String codeCoverageStateValue){
        CodeCoverageState[] values = CodeCoverageState.values();
        for (int i = 0; i < values.length; i++) {
            if(codeCoverageStateValue.equals(values[i].value())){
                return values[i];
            }
        }
        throw new IllegalArgumentException("[CodeCoverage]: mutation state value provided " + codeCoverageStateValue + " is not a valid mutation state");
    }
}
