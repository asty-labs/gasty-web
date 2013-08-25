import gasty.GrailsFormEngineFactory
import gasty.DefaultGrailsFormEngineFactory

class BootStrap {

    def init = { servletContext ->
        GrailsFormEngineFactory.instance = new DefaultGrailsFormEngineFactory()
    }
    def destroy = {
    }
}
