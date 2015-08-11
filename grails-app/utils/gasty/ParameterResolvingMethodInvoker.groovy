package gasty

import com.jasty.core.MethodInvoker
import com.jasty.core.Form
import com.jasty.core.ParameterProvider
import com.jasty.core.EventArgs
import com.jasty.core.UploadedFile
import org.codehaus.groovy.grails.commons.GrailsApplication

import java.lang.reflect.InvocationTargetException
import org.springframework.core.LocalVariableTableParameterNameDiscoverer
import com.jasty.core.ComponentProxy

class ParameterResolvingMethodInvoker implements MethodInvoker {

    private static final String EVENT_PREFIX = "EVT.";
	GrailsApplication grailsApplication

	ParameterResolvingMethodInvoker(GrailsApplication grailsApplication) {
		this.grailsApplication = grailsApplication
	}

    @Override
    void invoke(Form form, ParameterProvider parameterProvider) {

        def eventHandler = parameterProvider.getParameter("eventHandler");
        def method
		Class checkClass = form.getClass()
		while (checkClass && !method) {
			method = checkClass.declaredMethods.find { it.name == eventHandler }
			checkClass = checkClass.superclass
		}
        if(!method) throw new NoSuchMethodException(eventHandler)
        def paramNames = new LocalVariableTableParameterNameDiscoverer().getParameterNames(method)
        def paramTypes = method.parameterTypes
        def parameters = []
        for (def i=0;i<paramNames.length;i++) {
            parameters.add(resolveParameterValue(paramNames[i], paramTypes[i], form, parameterProvider))
        }
        try {
            method.invoke(form, parameters.toArray())
        }
        catch(InvocationTargetException e) {
            throw e.targetException
        }
    }

    protected Object resolveParameterValue(String name, Class type, Form form, ParameterProvider parameterProvider) {
        if(type == Object || type == EventArgs) return extractEventArgs(form, parameterProvider)
        if(name.startsWith("event")) {
            def parameterName = name.substring("event".length())
            parameterName = parameterName.substring(0, 1).toLowerCase() + parameterName.substring(1)
            def eventArgs = extractEventArgs(form, parameterProvider)
            def value = eventArgs.get(parameterName)
            if(ComponentProxy.isAssignableFrom(type)) return form.$$(type, value)
            return parseValue(type, value)
        }
        if(ComponentProxy.isAssignableFrom(type)) return form.$$(type, name)
        def value = parameterProvider.getParameter(form.globalizeId(name))
		if(type == UploadedFile) return parameterProvider.getFile(form.globalizeId(name))
        parseValue(type, value)
    }

    protected Object parseValue(Class type, String value) {
        if(type == long) return (value ?: 0) as long
		if(type == Long) return (value ? value as long: null)
        if(type == int) return (value ?:0) as int
	    if(type == boolean) return ((value ?:0) as int) > 0
	    if(value == null) return null
        if(type == String) return value
		if(type.isEnum()) return Enum.valueOf(type, value)
        if(grailsApplication.isDomainClass(type)) return type.get(value as long)
        throw new RuntimeException("cannot resolve value for the parameter of type ${type.name}")
    }

    private static EventArgs extractEventArgs(Form form, ParameterProvider parameterProvider) {

        def args = new EventArgs()
        for(def key : parameterProvider.parameterNames) {
            if(key.startsWith(EVENT_PREFIX)) {
                def value = parameterProvider.getParameter(key)
                key = key.substring(EVENT_PREFIX.length())
                if("srcId" == key)
                    args.put("source", value.substring(form.getId().length() + 1))
                else
                    args.put(key, value)
            }
        }
        args
    }

}
