package gasty

import com.jasty.core.ClientSideFormPersister
import com.jasty.core.FormEngine
import com.jasty.core.ViewRenderer
import com.jasty.core.ParameterProvider
import org.codehaus.groovy.grails.web.pages.GroovyPagesTemplateEngine
import com.jasty.core.ComponentRenderingHelper
import com.jasty.components.Button
import com.jasty.components.Link
import com.jasty.core.RenderingContext
import com.jasty.core.Component
import com.jasty.components.TextBox
import com.jasty.components.CheckBox
import com.jasty.components.ComboBox
import com.jasty.core.Form

class StandardTagLib {

    static namespace = "m"

    GroovyPagesTemplateEngine groovyPagesTemplateEngine

    def formViewer = { attrs ->

        ParameterProvider parameterProvider = new RequestParameterProvider(params);
        ViewRenderer viewRenderer = new GspViewRenderer(groovyPagesTemplateEngine);
        FormEngine formEngine = new FormEngine(parameterProvider, viewRenderer, ClientSideFormPersister.getInstance());
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

    def button = { attrs ->
        render(Button, attrs, false)
    }

    def block = { attrs, body ->
        render(com.jasty.components.Block, attrs, body, false)
    }

    def write = { attrs ->
        out << (attrs.value as String).replaceAll("\n", "<br/>")
    }

    def link = { attrs, body ->
        render(Link, attrs, body, false)
    }

    def textBox = { attrs ->
        render(TextBox, attrs, !attrs.rows)
    }

    def comboBox = { attrs ->
        render(ComboBox, attrs, null, false)
    }

    def checkBox = { attrs ->
        render(CheckBox, attrs, true)
    }

    private <T extends Component> void render(Class<T> clazz, attrs, boolean voidTag) {
        render(clazz, attrs, null, voidTag)
    }

    private <T extends Component> void render(Class<T> clazz, attrs, Closure body) {
        render(clazz, attrs, body, false)
    }

    private <T extends Component> void render(Class<T> clazz, attrs, Closure body, boolean voidTag) {
        def component = clazz.newInstance()
        copy(component, attrs)
        RenderingContext.getInstance().registerComponent(component)
        if(voidTag) {
            out << ComponentRenderingHelper.getVoidTag(component)
        }
        else  {
            out << ComponentRenderingHelper.getStartTag(component)
            if(body) {
                out << body()
            }
            out << ComponentRenderingHelper.getEndTag(component)
        }
    }


    static copy(obj, params) {
        for (val in params) {
            def property = obj.hasProperty(val.key);
            if (property)
                obj.putAt(val.key, convert(property.type, val.value))
        }
    }

    static def convert(Class type, value) {
        if (Integer.isAssignableFrom(type)) {
            if (value instanceof String)
                return Integer.parseInt(value)
        }
        if (int.isAssignableFrom(type)) {
            if (value instanceof String)
                return Integer.parseInt(value).value
        }
        if (Boolean.isAssignableFrom(type)) {
            if (value instanceof String)
                return Boolean.parseBoolean(value)
        }
        if (boolean.isAssignableFrom(type)) {
            if (value instanceof String)
                return Boolean.parseBoolean(value).value
        }
        value
    }

}
