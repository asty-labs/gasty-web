package gasty

import com.jasty.core.ViewRenderer
import com.jasty.core.Form
import org.codehaus.groovy.grails.web.pages.GroovyPagesTemplateEngine
import com.jasty.js.JsContext
/**
 * Created with IntelliJ IDEA.
 * User: stas
 * Date: 11/15/12
 * Time: 1:41 AM
 * To change this template use File | Settings | File Templates.
 */
class GspViewRenderer implements ViewRenderer {

    GroovyPagesTemplateEngine groovyPagesTemplateEngine

    GspViewRenderer(GroovyPagesTemplateEngine groovyPagesTemplateEngine) {
        this.groovyPagesTemplateEngine = groovyPagesTemplateEngine
    }

    @Override
    String renderMainView(Form form, Object model) {
        renderFragment(form, "", model)
    }

    @Override
    String renderFragment(Form form, String fragmentName, Object model) {
        def viewPath = "forms/" + form.class.package.name + "/"
        if(fragmentName.startsWith("_"))
            viewPath += fragmentName
        else
            viewPath += form.mainViewName + (fragmentName ? "_" + fragmentName : "")

        def tmpl = groovyPagesTemplateEngine.createTemplate(viewPath.replace(".", "/") + ".gsp");

        // Provide a place to store the processed gsp
        def out = new StringWriter();

        // Process the gsp with the given model and write to the StringWriter
        if(model == null) model = [:]
        model = model + [currentForm: form]
        JsContext.execute(new Runnable() {

            @Override
            void run() {
                tmpl.make(model).writeTo(out);
            }
        })

        return out.toString()
    }
}
