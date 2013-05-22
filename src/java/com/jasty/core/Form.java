package com.jasty.core;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * This is the base class for all Forms. Forms are stateful presenters/controllers with event handler
 * methods, where FormEngine dispatches requests to.
 *
 * Form objects are serializable, so the fields that shouldn't or can't be serialized,
 * must be marked as transient.
 *
 * @author Stanislav Tkachev
 * @version 1.0
 *
 */
public abstract class Form extends Component {

    /**
     * When a component gets registered in Form and has no id, Form assigns it a generated one.
     * For subsequent requests the last used id should be tracked, to avoid name clashes.
     *
     */
	private int lastAssignedChildId;

    /**
     * FormEngine is injected on every request
     */
	protected transient FormEngine formEngine;

    /**
     * This flag is set, if the form is disposed (e.g. replaced by another one) and doesn't
     * need to be persisted.
     *
     */
    private transient boolean disposed;

    @Override
    public String getHtmlTag() {
        return "form";
    }

    /**
     * While rendering form view, every rendered component should be registered in the
     * Form-object via this method. The main purpose is to adjust component id, to make it
     * unique.
     *
     * @param component  to be registered
     */
    public void registerComponent(Component component) {
        component.setId(globalizeId(component.getId()));
    }

    /**
     * Processes id to ensure it's uniqueness among different Forms by prepending it
     * with the Form-id
     *
     * @param initialId to be adjusted
     * @return unique id
     */
	private String globalizeId(String initialId) {
		if(initialId == null)
			initialId = "c" + lastAssignedChildId++;
		return getClientId() + "." + initialId;
	}

    public String generateClientId(String id) {
        return globalizeId(id).replace(".", "_");
    }

    public void setFormEngine(FormEngine value) {
		formEngine = value;
	}

    /**
     * This method creates component proxy for the given type and id. The id is automatically
     * globalized and the restore-method is called on the component to read the state from
     * the request.
     *
     * @param type  component proxy class to be returned
     * @param id    component id within the Form
     * @param <T>   component type
     * @return      instance of the component proxy, with restored state
     */
	protected <T extends ComponentProxy> T $$(Class<T> type, String id) {
		try {
			T obj = type.newInstance();
			obj.setId(globalizeId(id));
			obj.restore(formEngine.getParameterMap());
			return obj;
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

    /**
     * This method creates component proxy for the given type and selector. The ids
     * in the selector are automatically globalized.
     *
     * @param type  component proxy class to be returned
     * @param query selector
     * @param <T>   component type
     * @return      instance of the component proxy, with restored state
     */
	protected <T extends ComponentProxy> T $(Class<T> type, String query) {
		try {
			T obj = type.newInstance();
			obj.setQuery(query.replace("#", "#" + getClientId() + "_"));
			return obj;
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

    /**
     * This method can be used in the event handlers of the Form to get request parameters.
     *
     * @param name  local parameter name
     * @return      value of the parameter
     *
     */
	protected String getParameter(String name) {
		return formEngine.getParameter(globalizeId(name));
	}

    protected MultipartFile getFile(String name) {
        return formEngine.getFile(globalizeId(name));
    }

    protected Map<String, Object> getParameters() {
        return formEngine.getParameterMap();
    }

    /**
     * This method can be used in the event handlers of the Form for rendering view fragments.
     *
     * @param fragmentName  fragment name to be rendered
     * @param model         model to be applied to the view
     * @return              HtmlFragment with rendered fragment
     *
     */
	protected HtmlFragment renderFragment(String fragmentName, Object model) {
		return formEngine.renderFragment(this, fragmentName, model);
	}

    protected HtmlFragment renderMainView(Form form, String suffix) {
        form.setId(getId() + "." + suffix);
        return formEngine.renderMainView(form);
    }

    /**
     * Renders the specified Form and replaces with it the current one
     *
     * @param form  Form to overwrite the current one
     */
    protected void replaceWith(Form form) {
        form.setId(getId());
        invoke("replaceWith", formEngine.renderMainView(form));
        disposed = true;
    }

    /**
     * This method can be overridden to provide model object for the Form's main view
     *
     * @return  model object for the main view
     *
     */
	protected Object prepareModel() {
		return null;
	}

    /**
     * This method is called after all components of the Form's main view are initialized. Typically,
     * you would override it to additionally manipulate some components, after the standard
     * initialization of the main view is done.
     *
     */
	protected void afterInit() {
	}

    /**
     * The javascript component is fixed to "Form", because we are not going to have
     * javascript component for every inherited Form-class
     *
     * @return  javascript component name
     */
	@Override
	protected String getClassName() {
		return "Form";
	}

    /**
     * Sends Form's state to the client
     *
     * @param state state representation for the client
     *
     */
	protected void update(String state) {
        if(disposed) return;
		invoke("update", state);
	}

    protected abstract void processThrowable(Throwable t);
}
