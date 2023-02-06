package org.arfna.method.blog.images;

import com.google.gson.annotations.Expose;
import org.arfna.method.blog.images.gson.Thumbnail;
import org.arfna.method.common.MethodResponse;

import java.io.Serializable;

public class ImageIdResponse extends MethodResponse implements Serializable {
    private static final long serialVersionUID = 6646388683784852349L;

    @Expose private String imageId;
    @Expose private Thumbnail thumbnail;

    public ImageIdResponse withImageId(String imageId) {
        this.imageId = imageId;
        return this;
    }

    public ImageIdResponse withThumbnail(Thumbnail thumbnail) {
        this.thumbnail = thumbnail;
        return this;
    }
    
    public String getImageId() {
        return this.imageId;
    }
}
