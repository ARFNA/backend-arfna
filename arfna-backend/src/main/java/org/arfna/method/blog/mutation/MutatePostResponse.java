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

    private void prepareForSerialization() {
        if (this.allPosts != null)
            this.allPosts.stream().filter(x -> x.getAuthor() != null).forEach(x -> {
                x.getAuthor().setPostsToNull();
                x.getAuthor().setPassword(null);
                x.getAuthor().setRole(null);
                x.getAuthor().setEmailAddress(null);
            });
        if (this.post != null && this.post.getAuthor() != null) {
            this.post.getAuthor().setPostsToNull();
            this.post.getAuthor().setPassword(null);
            this.post.getAuthor().setRole(null);
            this.post.getAuthor().setEmailAddress(null);
        }
    }

}
