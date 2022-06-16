package org.arfna.method.blog.images;

import com.google.gson.annotations.Expose;
import org.arfna.method.common.MethodResponse;

import java.io.Serializable;

public class ImageIdResponse extends MethodResponse implements Serializable {
    private static final long serialVersionUID = 6646388683784852349L;

    @Expose private String imageId;

    public ImageIdResponse withImageId(String imageId) {
        this.imageId = imageId;
        return this;
    }
}
