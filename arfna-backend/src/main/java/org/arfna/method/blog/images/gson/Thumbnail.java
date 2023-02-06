package org.arfna.method.blog.images.gson;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class Thumbnail implements Serializable {
    private static final long serialVersionUID = 969807154766850796L;

    @Expose private String base64;
    @Expose private String extension;

    public String getBase64() {
        return base64;
    }

    public String getExtension() {
        return extension;
    }

    public Thumbnail withBase64(String base64) {
        this.base64 = base64;
        return this;
    }

    public Thumbnail withExtension(String extension) {
        this.extension = extension;
        return this;
    }
}
