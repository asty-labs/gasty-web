package gasty

import com.jasty.js.JsExpression
import org.codehaus.groovy.grails.web.pages.GroovyPagesTemplateEngine
import com.jasty.servlet.FormEngineServlet

class GastyController {

    GroovyPagesTemplateEngine groovyPagesTemplateEngine

    def doAction() {
        JsExpression expr = GrailsFormEngineFactory.instance.getFormEngine(params, groovyPagesTemplateEngine).processEvent();
        FormEngineServlet.writeScript(expr, request, response)
        render("")
    }
}
