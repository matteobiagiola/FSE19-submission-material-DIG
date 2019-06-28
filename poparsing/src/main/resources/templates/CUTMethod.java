
// PO Name: {{ poName }}

public void {{ methodName }} ({{ methodParameters }}){
    if(this.currentPage instanceof {{ pageObject }}){
        {{ pageObject }} page = ({{ pageObject }}) this.currentPage;
        {{ methodBody }}
    }
    {% if exceptions %}
    else{
        throw new NotInTheRightPageObjectException("{{ methodName }}: expected {{ pageObject }}, found " + this.currentPage.getClass().getSimpleName());
    }
    {% endif %}
}