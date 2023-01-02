package org.arfna.method.blog;

import org.arfna.api.version.EVersion;
import org.arfna.database.entity.Post;
import org.arfna.util.logger.ArfnaLogger;

import java.util.List;

public class GetBlogHelper {

    public GetBlogResponse getAllPublishedBlogs(EVersion version) {
        ArfnaLogger.debug(this.getClass(), "Getting all published blogs");
        GetBlogResponse response = new GetBlogResponse();
        List<Post> publishedBlogs = version.getDatabaseUtil().getPublishedPosts();
        response.setPosts(publishedBlogs);
        return response;
    }

}
