package gasty

import org.springframework.context.ApplicationContextAware
import org.springframework.context.ApplicationContext
import org.codehaus.groovy.grails.commons.GrailsApplication

/**
 * Created with IntelliJ IDEA.
 * User: stas
 * Date: 8/26/13
 * Time: 9:42 PM
 * To change this template use File | Settings | File Templates.
 */
@Singleton
class ServiceUtils implements ApplicationContextAware {

    private ApplicationContext applicationContext

    @Override
    void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext
    }

    static <T> T lookup(Class<T> beanClass) {
        def name = beanClass.simpleName
        name = name.substring(0, 1).toLowerCase() + name.substring(1)
        (T)getInstance().applicationContext.getBean(name)
    }

    static GrailsApplication getGrailsApplication() {
        lookup(GrailsApplication)
    }
}
