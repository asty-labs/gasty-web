package gasty

import com.jasty.js.JsExpression
import org.codehaus.groovy.grails.web.pages.GroovyPagesTemplateEngine
import com.jasty.servlet.FormEngineServlet

class FormEngineController {

    GroovyPagesTemplateEngine groovyPagesTemplateEngine

	static allowedMethods = [doAction: 'POST']

    def doAction() {
        JsExpression expr = GrailsFormEngineFactory.instance.getFormEngine(params, groovyPagesTemplateEngine).processEvent();
        FormEngineServlet.writeScript(expr, request, response)
	    if(!response.isCommitted())
            render("")
    }
}
