package org.arfna.method.blog.mutation;

import org.arfna.api.version.EVersion;
import org.arfna.database.entity.Subscriber;
import org.arfna.method.common.EValidationMessage;
import org.arfna.method.common.ValidationMessage;

public class MutatePostApiV1 implements IMutatePostApi {

    public MutatePostResponse getResponse(MutatePostPayload payload, EVersion version, Subscriber subscriber) {
        EPostMutation mutation = payload.getMutation();
        MutatePostHelper helper = new MutatePostHelper();
        if (mutation == EPostMutation.GET_FOR_SUBSCRIBER) {
            return helper.getPostsForSubscriber(version, subscriber);
        } if (mutation == EPostMutation.GET_EXISTING_POST) {
            return helper.getExistingPost(payload, version, subscriber);
        } if (mutation == EPostMutation.SAVE) {
            return helper.savePost(payload, version, subscriber);
        } if (mutation == EPostMutation.SUBMIT) {
            return helper.submitPost(payload, version, subscriber);
        } if (mutation == EPostMutation.ACCEPT) {
            return helper.acceptPost(payload, version, subscriber);
        } if (mutation == EPostMutation.PUBLISH) {
            return helper.publishPost(payload, version, subscriber);
        }
        MutatePostResponse response = new MutatePostResponse();
        response.addValidationMessage(new ValidationMessage(EValidationMessage.INVALID_API));
        return response;
    }

}
