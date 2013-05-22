package gasty

import com.jasty.core.ViewRenderer
import com.jasty.core.Form
import org.springframework.web.servlet.mvc.Controller
import com.jasty.core.RenderingContext
import org.codehaus.groovy.grails.web.pages.GroovyPagesTemplateEngine
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
            viewPath += form.class.simpleName + (fragmentName ? "_" + fragmentName : "")

        def tmpl = groovyPagesTemplateEngine.createTemplate(viewPath.replace(".", "/") + ".gsp");

        // Provide a place to store the processed gsp
        def out = new StringWriter();

        // Process the gsp with the given model and write to the StringWriter
        if(model == null) model = [:]
        tmpl.make(model + [currentForm: form]).writeTo(out);

        return out.toString()
    }
}
