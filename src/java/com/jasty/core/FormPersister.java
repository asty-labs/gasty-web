package com.jasty.core;

public interface FormPersister {
    
    Form lookup(String key);
    
    String persist(Form form);
}
