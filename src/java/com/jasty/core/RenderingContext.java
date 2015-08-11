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

    private ComponentRegistry componentRegistry;
    private List<Component> components = new ArrayList<Component>();
	private static ThreadLocal<RenderingContext> registries = new ThreadLocal<RenderingContext>();

    public RenderingContext(ComponentRegistry componentRegistry) {
        this.componentRegistry = componentRegistry;
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
		return registries.get();
	}

    /**
     * Adds component to the rendering registry
     *
     * @param component
     */
    public void registerComponent(Component component) {
        componentRegistry.registerComponent(component);
        components.add(component);
    }

    public Collection<Component> getComponents() {
        return components;
    }

    public ComponentRegistry getComponentRegistry() {
        return componentRegistry;
    }
}
