package com.jasty.core;

import java.io.IOException;
import java.io.InputStream;

public interface UploadedFile {

    String getName();

    String getOriginalFilename();

    String getContentType();

    boolean isEmpty();

    long getSize();

    byte[] getBytes() throws IOException;

    InputStream getInputStream() throws IOException;
}
