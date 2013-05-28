package gasty

import com.jasty.core.Component
import com.jasty.core.RenderingContext
import com.jasty.core.ComponentRenderingHelper

/**
 * Created with IntelliJ IDEA.
 * User: stas
 * Date: 5/28/13
 * Time: 1:07 AM
 * To change this template use File | Settings | File Templates.
 */
class RenderingHelper {

    static <T extends Component> void render(Class<T> clazz, Writer out, attrs, boolean voidTag) {
        render(clazz, out, attrs, null, voidTag)
    }

    static <T extends Component> void render(Class<T> clazz, Writer out, attrs, Closure body) {
        render(clazz, out, attrs, body, false)
    }

    static <T extends Component> void render(Class<T> clazz, Writer out, attrs, Closure body, boolean voidTag) {
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
