package com.jasty.core;

import com.jasty.utils.SerializationUtils;
import org.apache.commons.codec.binary.Base64;

public class ClientSideFormPersister implements FormPersister {

    @Override
    public Form lookup(String key) {
        return (Form) SerializationUtils.deserializeObject(Base64.decodeBase64(key));
    }

    @Override
    public String persist(Form form) {
        byte[] bytes = SerializationUtils.serializeObject(form);
        return Base64.encodeBase64String(bytes);
    }
}
