package gasty

import com.jasty.core.ParameterProvider
import org.codehaus.groovy.grails.web.servlet.mvc.GrailsParameterMap
import javax.servlet.http.HttpServletRequest
import org.springframework.web.multipart.MultipartFile

/**
 * Created with IntelliJ IDEA.
 * User: stas
 * Date: 11/15/12
 * Time: 1:43 AM
 * To change this template use File | Settings | File Templates.
 */
class RequestParameterProvider implements ParameterProvider {

    GrailsParameterMap parameters

    RequestParameterProvider(GrailsParameterMap parameters) {
        this.parameters = parameters
    }

    @Override
    String getParameter(String name) {
        parameters[name]
    }

    @Override
    MultipartFile getFile(String name) {
        parameters[name]
    }

    @Override
    Map<String, Object> getParameterMap() {
        parameters
    }
}
