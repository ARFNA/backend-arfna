package org.arfna.method.blog.images;

import com.google.gson.annotations.Expose;
import org.arfna.database.entity.Post;

import java.io.Serializable;

public class ImageIdPayload implements Serializable {
    private static final long serialVersionUID = 2829523806918065888L;

    @Expose private EImageRequest requestType;
    @Expose private Post post;

    public EImageRequest getRequestType() {
        return requestType;
    }

    public Post getPost() {
        return post;
    }
}
