package com.jasty.core;

import com.jasty.js.JsClosure;
import com.jasty.js.JsContext;
import com.jasty.js.JsExpression;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * This is the central class for managing Form events, rendering views and
 * resolving entry points
 *
 * @author Stanislav Tkachev
 * @version 1.0
 *
 */
public class FormEngine {

    private final ParameterProvider parameterProvider;
    private final ViewRenderer viewRenderer;
    private FormPersister formPersister;

    public FormEngine(ParameterProvider parameterProvider,
                      ViewRenderer viewRenderer, FormPersister formPersister) {

        this.parameterProvider = parameterProvider;
        this.viewRenderer = viewRenderer;
        this.formPersister = formPersister;
    }

    /**
     * Resolves Form-object from the entry point class name and parameters.
     * The entry point class must have parameterless constructor and
     * either implement FormFactory or be a Form.
     *
     * @param entryPointClass
     * @param parameters
     * @return resolved form
     *
     */
    public static Form createInitialForm(String entryPointClass, String parameters)
            throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Object obj = Class.forName(entryPointClass).newInstance();
        if(obj instanceof FormFactory) return ((FormFactory)obj).createForm(parameters);
        if(obj instanceof Form) return (Form)obj;
        throw new RuntimeException("Wrong class: must be either FormFactory or Form");
    }
    
    protected String getParameter(String key) {
        return parameterProvider.getParameter(key);
    }

    protected MultipartFile getFile(String key) {
        return parameterProvider.getFile(key);
    }

    protected Map<String, Object> getParameterMap() {
        return parameterProvider.getParameterMap();
    }

    /**
     * Renders main view for the Form, using the model returned by prepareModel-method.
     * Generates javascript to initialize all rendered components.
     *
     * @param form  Form to be rendered
     * @return      HtmlFragment with rendered HTML and initialization script
     */
    public HtmlFragment renderMainView(final Form form) {
        form.setFormEngine(this);
        final StringBuilder builder = new StringBuilder();
        builder.append(ComponentRenderingHelper.getStartTag(form));
        final Object model = form.prepareModel();
        final RenderingContext reg = new RenderingContext(form);
        reg.collect(new Runnable() {

            @Override
            public void run() {
                builder.append(viewRenderer.renderMainView(form, model));
            }
        });
        builder.append(ComponentRenderingHelper.getEndTag(form));
        JsClosure script = new JsClosure(new Runnable() {
            @Override
            public void run() {
                form.init();
                for (Component comp : reg.getComponents()) {
                    comp.init();
                }
                form.afterInit();
                updateForm(form);
            }
        });
        return new HtmlFragment(builder.toString(), script);
    }

    /**
     * Renders view fragment for the Form, using the specified model.
     * Generates javascript to initialize all rendered components.
     *
     * @param form      Form of the fragment to be rendered
     * @param fragment  fragment name
     * @param model     model for the fragment view
     * @return      HtmlFragment with rendered HTML and initialization script
     */
    public HtmlFragment renderFragment(final Form form, final String fragment, final Object model) {
        form.setFormEngine(this);
        final RenderingContext reg = new RenderingContext(form);
        final StringBuilder builder = new StringBuilder();
        reg.collect(new Runnable() {

            @Override
            public void run() {
                builder.append(viewRenderer.renderFragment(form, fragment, model));
            }
        });
        JsClosure script = new JsClosure(new Runnable() {
            @Override
            public void run() {
                for (Component comp : reg.getComponents()) {
                    comp.init();
                }
            }
        });
        return new HtmlFragment(builder.toString(), script);
    }

    /**
     * This method dispatches event to the handler method of the appropriate Form. Dispatching
     * data are obtained from the parameter provider.
     *
     * @return              JsExpression to be sent as the response
     */
    public JsExpression processEvent() {
        return JsContext.execute(new Runnable() {
            @Override
            public void run() {
                executeMethod();
            }
        });
    }

    public void runInContext(Form form, Runnable action) {
        form.setFormEngine(this);
        try {
            action.run();
        }
        catch(FormEngineException e) {
            form.processThrowable(e.getCause());
        }
        catch(Throwable t) {
            form.processThrowable(t);
        }
        updateForm(form);
    }

    private void executeMethod() {
        final Form form = formPersister.lookup(getParameter("state"));
        form.setFormEngine(this);
        final String eventHandler = getParameter("eventHandler");
        final EventArgs args = extractEventArgs(parameterProvider.getParameterMap(), form);
        runInContext(form, new Runnable() {
            @Override
            public void run() {
                Method method = null;
                try {
                    method = form.getClass().getMethod(eventHandler, EventArgs.class);
                    method.invoke(form, args);
                } catch (NoSuchMethodException e) {
                    throw new FormEngineException(e);
                } catch (InvocationTargetException e) {
                    throw new FormEngineException(e.getTargetException());
                } catch (IllegalAccessException e) {
                    throw new FormEngineException(e);
                }
            }
        });
    }

    private void updateForm(Form form) {
        form.update(formPersister.persist(form));
    }

    private static final String EVENT_PREFIX = "EVT.";

    /**
     * Fills EventArgs object from the request parameters
     *
     * @param map       request parameters
     * @param form      form, owning the event
     * @return          filled out EventArgs-object
     */
    private static EventArgs extractEventArgs(Map<String, Object> map, Form form) {

        EventArgs args = new EventArgs();
        for(String key : map.keySet()) {
            if(key.startsWith(EVENT_PREFIX)) {
                String value = (String)map.get(key);
                key = key.substring(EVENT_PREFIX.length());
                if("srcId".equals(key))
                    args.setSrcId(value.substring(form.getId().length() + 1));
                else
                    args.put(key, value);
            }
        }
        return args;
    }

    private static class FormEngineException extends RuntimeException {

        public FormEngineException(Throwable t) {
            super(t);
        }
    }
}
