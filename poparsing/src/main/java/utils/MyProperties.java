package utils;

public class MyProperties {

    public final static String base_dir_path = System.getProperty("user.dir");
    public final static String home_dir = System.getProperty("user.home");

    public final static String page_object_interface_name = "PageObject";
    public final static String templates_path = "src/main/resources/templates";
    public final static String instantiated_template_path = "src/main/resources";
    public final static String instantiated_template_temp_path = "src/main/resources/temp";
    public final static String template_class_under_test_name = "ClassUnderTest.java";
    public final static String template_class_under_test_method_name = "CUTMethod.java";
    public final static String package_name_cut = "main";
    public final static String java_package_structure = "src/main/java";

    public static String starting_page_object_qualified_name;
    public static String path_to_project;
    public static String project_name;
    public static String po_package_in_project;
    public static String path_to_POs;
    public static boolean exceptions = false;
    public static boolean apogen = false;
    public static boolean quit_browser_to_reset = false;
    public static String class_under_test_name = "ClassUnderTest";
}
