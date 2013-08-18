package gasty

import com.jasty.core.ClientSideFormPersister
import com.jasty.core.FormEngine
import com.jasty.core.ViewRenderer
import com.jasty.core.ParameterProvider
import org.codehaus.groovy.grails.web.pages.GroovyPagesTemplateEngine
import com.jasty.core.Form
import com.jasty.core.SimpleExceptionHandler
import com.jasty.core.DefaultMethodInvoker

class StandardTagLib {

    static namespace = "jasty"

    GroovyPagesTemplateEngine groovyPagesTemplateEngine

    def formViewer = { attrs ->

        ParameterProvider parameterProvider = new RequestParameterProvider(params);
        ViewRenderer viewRenderer = new GspViewRenderer(groovyPagesTemplateEngine);
        FormEngine formEngine = new FormEngine(parameterProvider, viewRenderer, ClientSideFormPersister.getInstance(),
                new SimpleExceptionHandler(new DefaultMethodInvoker()));
        def form = FormEngine.createInitialForm(attrs.entryPoint, attrs.parameters)
        form.setId(attrs.id);
        def htmlFragment = formEngine.renderMainView(form);
        out << htmlFragment.getHtml()
        out << htmlFragment.getScript().encodeOnLoadAsHtml()
    }

    private Form getCurrentForm() {
        pageScope.currentForm as Form
    }

    def id = {attrs ->
        currentForm.generateClientId((String)attrs.id)
    }

    def resource = { attrs ->
        attrs.dir = pluginContextPath + "/" + attrs.dir
        out << g.resource(attrs)
    }
}
