package gasty

import com.jasty.js.JsExpression
import com.jasty.core.FormEngine
import com.jasty.core.ClientSideFormPersister
import com.jasty.core.ParameterProvider
import com.jasty.core.ViewRenderer
import org.codehaus.groovy.grails.web.pages.GroovyPagesTemplateEngine
import com.jasty.core.SimpleExceptionHandler
import com.jasty.core.DefaultMethodInvoker

class GastyController {

    GroovyPagesTemplateEngine groovyPagesTemplateEngine

    private String getUserAgent() {
        request.getHeader("user-agent").toLowerCase()
    }

    def doAction(String domain) {
        ParameterProvider parameterProvider = new RequestParameterProvider(params);
        ViewRenderer viewRenderer = new GspViewRenderer(groovyPagesTemplateEngine);

        JsExpression expr = new FormEngine(parameterProvider,
                viewRenderer,
                ClientSideFormPersister.getInstance(),
                new SimpleExceptionHandler(new DefaultMethodInvoker())).processEvent();

        if(response.isCommitted()) return;

        def script = expr.encode()

        response.characterEncoding = "UTF-8"
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Cache-Control", "must-revalidate"); // HTTP 1.1
        response.addHeader("Pragma", "no-cache"); // HTTP 1.0
        response.setDateHeader("Expires", 0); // Proxies.

        if(request.contentType.startsWith("multipart/form-data")) {
            // to avoid displaying download dialog put script in html-tag.
            // see jquery.form.js docs

            // STK: performance fix for firefox, when trying to render big javascript in textarea - use span containing comment instead
            if(userAgent.contains("firefox"))
                render("<span><!--" + script + "--></span>")
            else
                render("<textarea>" + script + "</textarea>")
        }
        else {
            response.setContentType("text/javascript");
            render(script);
        }
    }
}
