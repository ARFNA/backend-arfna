package org.arfna.method.blog.mutation;

import org.arfna.api.version.EVersion;
import org.arfna.database.DatabaseUtil;
import org.arfna.database.entity.Post;
import org.arfna.database.entity.Subscriber;
import org.arfna.method.common.EValidationMessage;
import org.arfna.method.common.ValidationMessage;
import org.arfna.method.password.middleware.ESubscriberRole;
import org.arfna.util.logger.ArfnaLogger;

import java.util.List;
import java.util.Optional;

public class MutatePostHelper {

    public MutatePostResponse getPostsForSubscriber(EVersion version, Subscriber subscriber) {
        ArfnaLogger.debug(this.getClass(), "Getting posts for subscriber with ID: " + subscriber.getId());
        MutatePostResponse response = new MutatePostResponse();
        Subscriber fromTableSubscriber = version.getDatabaseUtil().getSubscriber(subscriber.getId());
        if (fromTableSubscriber == null) {
            response.addValidationMessage(new ValidationMessage(EValidationMessage.SUBSCRIBER_DOES_NOT_EXIST));
            return response;
        }
        List<Post> posts = fromTableSubscriber.getPosts();
        response.setAllPosts(posts);
        return response;
    }

    public MutatePostResponse getExistingPost(MutatePostPayload payload, EVersion version, Subscriber subscriber) {
        ArfnaLogger.debug(this.getClass(), "Getting existing post");
        MutatePostResponse response = new MutatePostResponse();
        Post post = payload.getPost();
        Post fullPostFromDatabase = version.getDatabaseUtil().getPost(post.getId());
        MutatePostResponse validationResponse = getValidationResponseForExistingPost(subscriber, response, fullPostFromDatabase);
        if (validationResponse != null) return validationResponse;
        response.setPost(fullPostFromDatabase);
        return response;
    }

    public MutatePostResponse savePost(MutatePostPayload payload, EVersion version, Subscriber subscriber) {
        ArfnaLogger.debug(this.getClass(), "Updating given post for subscriber");
        // ensure that during save process, nothing else is allowed
        payload.markPostNotReady();
        // if the payload post ID exists, this means this is an existing post that exists in the table.
        return payload.getPost().getId() > 0 ? overwriteExistingPost(payload, version, subscriber) :
                addNewPost(payload, version, subscriber);
    }

    public MutatePostResponse submitPost(MutatePostPayload payload, EVersion version, Subscriber subscriber) {
        ArfnaLogger.debug(this.getClass(), "Updating given post and marking as submitted");
        payload.markInputPostAsSubmitted();
        return payload.getPost().getId() > 0 ? overwriteExistingPost(payload, version, subscriber) :
                addNewPost(payload, version, subscriber);
    }

    public MutatePostResponse acceptPost(MutatePostPayload payload, EVersion version, Subscriber subscriber) {
        MutatePostResponse response = new MutatePostResponse();
        if (!version.getMiddlewareHelper().isSubscriberAuthorized(Optional.of(subscriber), ESubscriberRole.MAINT_ROLE)) {
            response.setUnauthorized();
            return response;
        }
        payload.markInputPostAsAccepted();
        return overwriteExistingPost(payload, version, subscriber);
    }

    public MutatePostResponse publishPost(MutatePostPayload payload, EVersion version, Subscriber subscriber) {
        MutatePostResponse response = new MutatePostResponse();
        if (!version.getMiddlewareHelper().isSubscriberAuthorized(Optional.of(subscriber), ESubscriberRole.ADMIN_ROLE)) {
            response.setUnauthorized();
            return response;
        }
        payload.markInputPostAsPublished();
        return overwriteExistingPost(payload, version, subscriber);
    }

    private MutatePostResponse overwriteExistingPost(MutatePostPayload payload, EVersion version, Subscriber subscriber) {
        MutatePostResponse response = new MutatePostResponse();
        Post postToOverwrite = payload.getPost();
        version.getDatabaseUtil().updatePost(postToOverwrite);
        Post updatedPostInTable = version.getDatabaseUtil().getPost(payload.getPost().getId());
        response.setPost(updatedPostInTable);
        return response;
    }

    private MutatePostResponse addNewPost(MutatePostPayload payload, EVersion version, Subscriber subscriber) {
        MutatePostResponse response = new MutatePostResponse();
        Post postToWrite = payload.getPost();
        postToWrite.setAuthor(subscriber);
        int postId = version.getDatabaseUtil().createPost(postToWrite);
        if (postId > 0) {
            Post newPostInTable = version.getDatabaseUtil().getPost(postId);
            response.setPost(newPostInTable);
        } else {
            response.addValidationMessage(new ValidationMessage(EValidationMessage.POST_NOT_WRITTEN));
        }
        return response;
    }

    private MutatePostResponse getValidationResponseForExistingPost(Subscriber subscriber, MutatePostResponse response, Post fullPostFromDatabase) {
        if (fullPostFromDatabase == null) {
            response.addValidationMessage(new ValidationMessage(EValidationMessage.POST_DOES_NOT_EXIST));
            return response;
        }
        if (fullPostFromDatabase.getAuthor().getId() != subscriber.getId() && !subscriber.getRole().equals(ESubscriberRole.ADMIN_ROLE.getRoleName())) {
            response.addValidationMessage(new ValidationMessage(EValidationMessage.POST_NOT_BELONG_TO_SUBSCRIBER));
            return response;
        }
        return null;
    }

}
