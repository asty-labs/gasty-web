package gasty

import com.jasty.core.FormEngine
import org.codehaus.groovy.grails.web.pages.GroovyPagesTemplateEngine
import org.codehaus.groovy.grails.web.util.TypeConvertingMap

abstract class GrailsFormEngineFactory {
    static GrailsFormEngineFactory instance;

    abstract FormEngine getFormEngine(TypeConvertingMap params, GroovyPagesTemplateEngine groovyPagesTemplateEngine);
}
