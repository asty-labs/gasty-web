package com.jasty.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * This class is used to collect (register) all Components while rendering.
 * Used in FormEngine and in the component renderers (tags)
 *
 * @author Stanislav Tkachev
 * @version 1.0
 *
 */
public class RenderingContext {

    private Form form;
    private List<Component> components = new ArrayList<Component>();
	private static ThreadLocal<RenderingContext> registries = new ThreadLocal<RenderingContext>();

    public RenderingContext(Form form) {
        this.form = form;
    }

    /**
     * Calls action in the thread-static context of this instance
     *
     * @param action action delegate
     */
	public void collect(Runnable action) {
		RenderingContext previous = registries.get();
		try {
			registries.set(this);
			action.run();
		}
		finally {
			registries.set(previous);
		}
	}

    /**
     * Gets the current thread static instance
     *
     * @return rendering context
     */
	public static RenderingContext getInstance() {
		RenderingContext reg = registries.get();
		if(reg == null) throw new RuntimeException("RenderingContext not set!");
		return reg;
	}

    /**
     * Adds component to the rendering registry
     *
     * @param component
     */
    public void registerComponent(Component component) {
        form.registerComponent(component);
        components.add(component);
    }

    public Collection<Component> getComponents() {
        return components;
    }
}
