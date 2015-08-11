import gasty.GrailsFormEngineFactory
import gasty.DefaultGrailsFormEngineFactory

class BootStrap {

    def grailsApplication

    def init = { servletContext ->
        GrailsFormEngineFactory.instance = new DefaultGrailsFormEngineFactory(grailsApplication)
    }
    def destroy = {
    }
}
