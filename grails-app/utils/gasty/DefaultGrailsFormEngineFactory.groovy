package gasty

import com.jasty.core.FormEngine
import org.codehaus.groovy.grails.web.servlet.mvc.GrailsParameterMap
import org.codehaus.groovy.grails.web.pages.GroovyPagesTemplateEngine
import com.jasty.core.ParameterProvider
import com.jasty.core.ViewRenderer
import com.jasty.core.ClientSideFormPersister
import com.jasty.core.SimpleExceptionHandler
import com.jasty.core.DefaultMethodInvoker
import com.jasty.core.FormPersister
import com.jasty.core.MethodInvoker

class DefaultGrailsFormEngineFactory extends GrailsFormEngineFactory {

    private FormPersister formPersister = new ClientSideFormPersister()
    private MethodInvoker methodInvoker = new SimpleExceptionHandler(new DefaultMethodInvoker())

    @Override
    FormEngine getFormEngine(GrailsParameterMap params, GroovyPagesTemplateEngine groovyPagesTemplateEngine) {
        ParameterProvider parameterProvider = new RequestParameterProvider(params)
        ViewRenderer viewRenderer = new GspViewRenderer(groovyPagesTemplateEngine)
        new FormEngine(parameterProvider,
                viewRenderer,
                formPersister,
                methodInvoker)
    }
}
