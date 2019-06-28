package main;

import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

public class ClassUnderTestFormatter {

    public void formatClassUnderTest(String sourceCodeCutToInstrumentPath){
        File cutJavaFile = new File(sourceCodeCutToInstrumentPath);
        try {
            JavaClassSource cutClass = Roaster.parse(JavaClassSource.class, cutJavaFile);
            String cutClassFormatted = Roaster.format(cutClass.toString());
            this.writeFormattedClassUnderTestOnFile(cutJavaFile,cutClassFormatted);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void writeFormattedClassUnderTestOnFile(File cutJavaFile, String cutFormatted){
        try {
            Writer writer = new PrintWriter(cutJavaFile);
            writer.write(cutFormatted);
            writer.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
