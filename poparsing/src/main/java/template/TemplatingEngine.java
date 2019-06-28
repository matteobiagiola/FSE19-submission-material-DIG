package template;

import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.error.PebbleException;
import com.mitchellbosecke.pebble.template.PebbleTemplate;
import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import utils.MyProperties;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

public class TemplatingEngine {

    private final PebbleTemplate compiledTemplate;
    private final boolean isMethod;


    public TemplatingEngine(String templateName, boolean isMethod){
        checkNotNull(templateName);
        this.isMethod = isMethod;
        PebbleEngine engine = new PebbleEngine.Builder().autoEscaping(false).build();
        try {
            this.compiledTemplate = engine.getTemplate(MyProperties.templates_path + "/" + templateName);
        } catch (PebbleException e) {
            e.printStackTrace();
            throw new IllegalStateException();
        }
    }

    public void instantiateTemplate(String name, Map<String,Object> templateContext, boolean isCUT){
        Writer stringWriter = new StringWriter();
        try {
            this.compiledTemplate.evaluate(stringWriter, templateContext);
            String instantiatedTemplate = stringWriter.toString();
            String templatePath;
            if(this.isMethod){
                templatePath = MyProperties.instantiated_template_temp_path;
            }else{
                templatePath = MyProperties.instantiated_template_path;
            }
            Writer writer = new PrintWriter(templatePath + "/" + name + ".java", "UTF-8");
            if(isCUT){
                String cutFormatted = this.formatCUT(instantiatedTemplate);
                writer.write(cutFormatted);
            }else{
                writer.write(instantiatedTemplate);
            }
            writer.close();
        } catch (PebbleException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String formatCUT(String cutNotFormatted){
        JavaClassSource cutClass = Roaster.parse(JavaClassSource.class, cutNotFormatted);
        String cutClassFormatted = Roaster.format(cutClass.toString());
        return cutClassFormatted;
    }
}
