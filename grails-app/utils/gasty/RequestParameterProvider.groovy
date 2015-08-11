package gasty

import com.jasty.core.ParameterProvider
import org.codehaus.groovy.grails.web.util.TypeConvertingMap
import org.springframework.web.multipart.MultipartFile
import com.jasty.core.UploadedFile

/**
 * Created with IntelliJ IDEA.
 * User: stas
 * Date: 11/15/12
 * Time: 1:43 AM
 * To change this template use File | Settings | File Templates.
 */
class RequestParameterProvider implements ParameterProvider {

	TypeConvertingMap parameters

    RequestParameterProvider(TypeConvertingMap parameters) {
        this.parameters = parameters
    }

    @Override
    String getParameter(String name) {
        if(parameters[name] instanceof String) return parameters[name]
        if(parameters[name] instanceof String[]) return parameters[name][0]
        null
    }

    @Override
    UploadedFile getFile(String name) {
        if(!parameters.containsKey(name)) return null
        new MultipartFileWrapper(parameters[name] as MultipartFile)
    }

    @Override
    Collection<String> getParameterNames() {
        return parameters.keySet()
    }

    @Override
    String[] getParameterValues(String name) {
        if(parameters[name] instanceof String) return [parameters[name]]
        if(parameters[name] instanceof String[]) return (String[])parameters[name]
        new String[0]
    }
}
