package gasty

import com.jasty.core.FormEngine
import org.codehaus.groovy.grails.web.servlet.mvc.GrailsParameterMap
import org.codehaus.groovy.grails.web.pages.GroovyPagesTemplateEngine

abstract class GrailsFormEngineFactory {
    static GrailsFormEngineFactory instance;

    abstract FormEngine getFormEngine(GrailsParameterMap params, GroovyPagesTemplateEngine groovyPagesTemplateEngine);
}
