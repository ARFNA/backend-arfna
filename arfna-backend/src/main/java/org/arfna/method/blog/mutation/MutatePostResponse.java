package org.arfna.method.blog.mutation;

import com.google.gson.annotations.Expose;
import org.arfna.database.entity.Post;
import org.arfna.method.common.MethodResponse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MutatePostResponse extends MethodResponse implements Serializable {
    private static final long serialVersionUID = 1243752887775990940L;

    @Expose private List<Post> allPosts;
    @Expose private Post post;

    public void setAllPosts(List<Post> posts) {
        this.allPosts = posts == null ? new ArrayList<>() : posts;
        prepareForSerialization();
    }

    public List<Post> getAllPosts() {
        return allPosts;
    }

    public void setPost(Post post) {
        this.post = post;
        prepareForSerialization();
    }

    public Post getPost() {
        return post;
    }

    public void prepareForSerialization() {
        if (this.allPosts != null)
            this.allPosts.forEach(x -> x.setAuthor(null));
        if (this.post != null)
            this.post.setAuthor(null);
    }

}
