package gasty

import com.jasty.core.FormEngine
import org.codehaus.groovy.grails.web.pages.GroovyPagesTemplateEngine
import com.jasty.core.Form

class StandardTagLib {

    static namespace = "jasty"

    GroovyPagesTemplateEngine groovyPagesTemplateEngine

    def formViewer = { attrs ->

        FormEngine formEngine = GrailsFormEngineFactory.instance.getFormEngine(params, groovyPagesTemplateEngine)
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
}
