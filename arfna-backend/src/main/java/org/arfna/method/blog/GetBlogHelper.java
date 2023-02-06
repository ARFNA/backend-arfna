package org.arfna.method.blog;

import org.arfna.api.version.EVersion;
import org.arfna.database.entity.Post;
import org.arfna.method.common.EValidationMessage;
import org.arfna.method.common.ValidationMessage;
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

    public GetBlogResponse getPublishedBlogFromId(EVersion version, GetBlogPayload payload) {
        ArfnaLogger.debug(this.getClass(), "Getting published post from ID");
        GetBlogResponse response = new GetBlogResponse();
        Post post = version.getDatabaseUtil().getPost(payload.getPost().getId());
        if (post == null) {
            ArfnaLogger.warn(this.getClass(), "No post found with specified ID");
            response.addValidationMessage(new ValidationMessage(EValidationMessage.POST_DOES_NOT_EXIST));
            return response;
        }
        if (!post.isPublished()) {
            ArfnaLogger.warn(this.getClass(), "Specified post is not published");
            response.addValidationMessage(new ValidationMessage(EValidationMessage.INVALID_POST_PERMISSIONS));
            return response;
        }
        return response.withPost(post);
    }

}
