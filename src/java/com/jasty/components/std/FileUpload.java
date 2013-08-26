package com.jasty.components.std;

import com.jasty.core.Component;
import com.jasty.core.ParameterProvider;
import com.jasty.core.UploadedFile;

import java.util.Map;

public class FileUpload extends Component {

    UploadedFile file;

    @Override
    public void restore(ParameterProvider parameterProvider) {
        if(parameterProvider.getKeys().contains(getId()))
            file = parameterProvider.getFile(getId());
    }

    public UploadedFile getFile() {
        return file;
    }

    @Override
    public String getHtmlTag() {
        return "input";
    }

    @Override
    protected void fillHtmlAttributes(Map<String, String> attributes) {
        attributes.put("type", "file");
    }
}
