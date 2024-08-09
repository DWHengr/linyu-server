package com.cershy.linyuserver.utils;

import org.springframework.core.io.ByteArrayResource;

public class FileUtil {
    public static ByteArrayResource createByteArrayResource(byte[] content, String filename) {
        return new ByteArrayResource(content) {
            @Override
            public String getFilename() {
                return filename;
            }
        };
    }
}
