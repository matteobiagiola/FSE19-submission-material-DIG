package main;

import graph.Connection;
import graph.GraphBuilder;
import graph.GraphExporter;
import org.apache.log4j.Logger;
import org.jgrapht.graph.DirectedPseudograph;
import parsing.MethodProcessor;
import parsing.PoFinder;
import template.ClassUnderTestCreator;
import spoon.reflect.declaration.CtClass;
import utils.MyProperties;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;

public class Main {

    final static Logger logger = Logger.getLogger(Main.class);

    public static void main(String[] args){

        checkArgument(args.length != 0, "Either pass \'create_properties\' or \'load_properties\' as argument");

        String arg = args[0];
        if(arg.equals("create_properties")){
            createConfigProperties();
            logger.debug("Please fill " + MyProperties.base_dir_path + "/config.properties" + " and re-run the program with \'load_properties\' option");
        }else if(arg.equals("load_properties")){
            loadProperties();
            logger.debug("Path to page objects: " + MyProperties.path_to_POs);
            PoFinder poFinder = new PoFinder();
            Map<String, List<CtClass<?>>> pageObjectsMap = poFinder.getPoParsedRepresentations(new File(MyProperties.path_to_POs));
            List<CtClass<?>> parsedPOs = pageObjectsMap.get(MyProperties.page_object_interface_name);
            //List<CtClass<?>> parsedParametricPOs = pageObjectsMap.get(MyProperties.parametric_page_object_component_interface_name);

            MethodProcessor methodProcessor = new MethodProcessor(parsedPOs);
            List<CtClass<?>> modifiedParsedPOs = methodProcessor.renameMethodsOfPO();

            /*modifiedParsedPOs.stream().forEach(parsedPO -> {
                String methodNames = parsedPO.getMethods().stream().map(ctMethod -> ctMethod.getSimpleName()).collect(Collectors.joining("\n"));
                logger.debug("PO name: " + parsedPO.getSimpleName() + "\nPO methods: " + methodNames);
            });*/

            GraphBuilder graphBuilder = new GraphBuilder(modifiedParsedPOs);
            DirectedPseudograph<CtClass<?>, Connection> graph = graphBuilder.buildGraph();
            if(MyProperties.apogen){
                logger.debug("Graph DOT file name: " + MyProperties.base_dir_path + "/" + MyProperties.project_name + "-apogen");
            }else{
                logger.debug("Graph DOT file name: " + MyProperties.base_dir_path + "/" + MyProperties.project_name);
            }
            GraphExporter graphExporter = new GraphExporter(graph);
            if(MyProperties.apogen){
                graphExporter.export(MyProperties.project_name + "-apogen");
            }else{
                graphExporter.export(MyProperties.project_name);
            }

            ClassUnderTestCreator classUnderTestCreator = new ClassUnderTestCreator(parsedPOs);
            classUnderTestCreator.createCUT();
        }

    }

    private static void createConfigProperties(){
        Properties prop = new Properties();
        try
        {
            OutputStream output = new FileOutputStream(MyProperties.base_dir_path + "/config.properties");

            prop.setProperty("starting_page_object_qualified_name", "");
            prop.setProperty("project_name", "");
            prop.setProperty("path_to_POs", "");

            prop.store(output, null);

            output.close();

        } catch (FileNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private static void loadProperties(){
        Properties prop = new Properties();
        File file = new File(MyProperties.base_dir_path + "/config.properties");
        checkState(file.exists(), MyProperties.base_dir_path + "/config.properties does not exist. Please create it running the program with \'create_properties\' option" );
        try
        {
            InputStream input = new FileInputStream(MyProperties.base_dir_path + "/config.properties");

            prop.load(input);

            MyProperties.starting_page_object_qualified_name = prop.getProperty("starting_page_object_qualified_name");
            MyProperties.project_name = prop.getProperty("project_name");
            MyProperties.path_to_project = MyProperties.home_dir + "/" + prop.getProperty("path_to_project");
            MyProperties.po_package_in_project = prop.getProperty("po_package_in_project");
            MyProperties.path_to_POs = MyProperties.path_to_project + "/" + MyProperties.project_name + "/"
                    + MyProperties.java_package_structure + "/" + MyProperties.po_package_in_project;
            MyProperties.exceptions = Boolean.valueOf(prop.getProperty("exceptions"));
            MyProperties.apogen = Boolean.valueOf(prop.getProperty("apogen"));
            MyProperties.quit_browser_to_reset = Boolean.valueOf(prop.getProperty("quit_browser_to_reset"));
            if(MyProperties.apogen){
                MyProperties.class_under_test_name = MyProperties.class_under_test_name + "Apogen";
            }

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
