package org.arfna.method.blog;

import com.google.gson.annotations.Expose;
import org.arfna.database.entity.Post;

import java.io.Serializable;

public class GetBlogPayload implements Serializable {
    private static final long serialVersionUID = 2143313390679642790L;

    @Expose private EGetBlogRequest requestType;
    @Expose private Post post;

    public EGetBlogRequest getRequestType() {
        return requestType;
    }

    public Post getPost() {
        return post;
    }
}
