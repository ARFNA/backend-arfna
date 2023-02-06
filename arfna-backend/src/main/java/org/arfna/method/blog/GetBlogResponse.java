package org.arfna.method.blog;

import com.google.gson.annotations.Expose;
import org.arfna.database.entity.Post;
import org.arfna.method.common.MethodResponse;

import java.io.Serializable;
import java.util.List;

public class GetBlogResponse extends MethodResponse implements Serializable {
    private static final long serialVersionUID = -4309104866174156692L;

    @Expose private List<Post> posts;
    @Expose private Post post;

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
        prepareForSerialization();
    }

    public GetBlogResponse withPost(Post post) {
        this.post = post;
        prepareForSerialization();
        return this;
    }

    private void prepareForSerialization() {
        if (this.posts != null)
            this.posts.forEach(x -> {
                x.getAuthor().setPostsToNull();
                x.getAuthor().setPassword(null);
                x.getAuthor().setRole(null);
                x.getAuthor().setEmailAddress(null);
            });
        if (this.post != null) {
            post.getAuthor().setPostsToNull();
            post.getAuthor().setPassword(null);
            post.getAuthor().setRole(null);
            post.getAuthor().setEmailAddress(null);
        }
    }
}
