package com.jasty.core;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * This interface is an abstraction to get request parameters. Used by FormEngine
 *
 * @author Stanislav Tkachev
 * @version 1.0
 *
 */
public interface ParameterProvider {

    String getParameter(String name);

    MultipartFile getFile(String name);

    Map<String, Object> getParameterMap();
    
}
