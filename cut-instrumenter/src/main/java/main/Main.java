package main;

import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args){
        boolean formatCUT = Boolean.valueOf(MyProperties.getInstance().getProperty("formatCUT"));
        String sourceCodeCutToInstrumentPath = System.getProperty("user.home")
                + "/" + MyProperties.getInstance().getProperty("sourceCodeCutToInstrumentPath");
        if(formatCUT){
            ClassUnderTestFormatter classUnderTestFormatter = new ClassUnderTestFormatter();
            classUnderTestFormatter.formatClassUnderTest(sourceCodeCutToInstrumentPath);
        }else{
            boolean instrumentCutForTestSuiteRun = Boolean.valueOf(MyProperties.getInstance().getProperty("instrumentCutForTestSuiteRun"));
            boolean instrumentCutForEvosuiteRun = Boolean.valueOf(MyProperties.getInstance().getProperty("instrumentCutForEvosuiteRun"));
            LineInstrumenter lineInstrumenter = new LineInstrumenter(instrumentCutForTestSuiteRun, instrumentCutForEvosuiteRun);
            List<Integer> lines = lineInstrumenter.instrumentLinesForModelTransitions();
            String linesAsString = lines.stream().map(String::valueOf).collect(Collectors.joining(":"));
            System.out.println("Lines to cover: " + lines.size() + " - " + linesAsString);
        }
    }
}
