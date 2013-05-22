package gasty

import java.beans.PropertyEditorSupport
import java.text.SimpleDateFormat
import org.springframework.beans.PropertyEditorRegistrar
import org.springframework.beans.PropertyEditorRegistry

/**
 * Created with IntelliJ IDEA.
 * User: stas
 * Date: 2/6/13
 * Time: 12:00 AM
 * To change this template use File | Settings | File Templates.
 */
class DatePropertyEditor extends PropertyEditorSupport {

    String getAsText() {
        def format = new SimpleDateFormat("dd.MM.yyyy")
        format.format(value)
    }

    void setAsText(String text) {
        value = Date.parse("dd.MM.yyyy", text)
    }
}

public class MyEditorRegistrar implements PropertyEditorRegistrar {
    public void registerCustomEditors(PropertyEditorRegistry propertyEditorRegistry) {
        propertyEditorRegistry.registerCustomEditor(Date, new DatePropertyEditor())
    }
}