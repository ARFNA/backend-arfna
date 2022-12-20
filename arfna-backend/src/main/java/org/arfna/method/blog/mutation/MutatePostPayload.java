package org.arfna.method.blog.mutation;

import com.google.gson.annotations.Expose;
import org.arfna.database.entity.Post;

import java.io.Serializable;

public class MutatePostPayload implements Serializable {
    private static final long serialVersionUID = -4382791942448091014L;

    @Expose private EPostMutation mutation;
    @Expose private Post post;

    public EPostMutation getMutation() {
        return mutation;
    }

    public Post getPost() {
        return post;
    }

    public void markPostNotReady() {
        this.post.setSubmitted(false);
        this.post.setAccepted(false);
        this.post.setPublished(false);
    }

    public void markInputPostAsSubmitted() {
        this.post.setSubmitted(true);
        this.post.setAccepted(false);
        this.post.setPublished(false);
    }

    public void markInputPostAsAccepted() {
        this.post.setSubmitted(true);
        this.post.setAccepted(true);
        this.post.setPublished(false);
    }

    public void markInputPostAsPublished() {
        this.post.setSubmitted(true);
        this.post.setAccepted(true);
        this.post.setPublished(true);
    }
}
