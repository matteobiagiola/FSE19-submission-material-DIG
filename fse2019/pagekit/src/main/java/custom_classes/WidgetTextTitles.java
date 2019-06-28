package custom_classes;

import po_utils.TestData;

public enum WidgetTextTitles implements TestData, Widget {
    HELLO ("Hello, I'm Pagekit"),
    HELLO_COPY ("Hello, I'm Pagekit - Copy"),
    POWERED ("Powered by Pagekit"),
    POWERED_COPY ("Powered by Pagekit - Copy"),
    FOO ("Hi, I'm foo");

    private final String widgetText;

    WidgetTextTitles(String widgetText){
        this.widgetText = widgetText;
    }

    public String value(){
        return this.widgetText;
    }
}
