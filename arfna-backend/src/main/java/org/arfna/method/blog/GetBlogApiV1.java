package org.arfna.method.blog;

import org.arfna.api.version.EVersion;
import org.arfna.method.common.EValidationMessage;
import org.arfna.method.common.ValidationMessage;

public class GetBlogApiV1 implements IGetBlogApi{

    @Override
    public GetBlogResponse getResponse(GetBlogPayload payload, EVersion version) {
        EGetBlogRequest request = payload.getRequestType();
        GetBlogHelper helper = new GetBlogHelper();
        if (request == EGetBlogRequest.GET_ALL_PUBLISHED) {
            return helper.getAllPublishedBlogs(version);
        }
        if (request == EGetBlogRequest.GET_PUBLISHED_POST_FROM_ID) {
            return helper.getPublishedBlogFromId(version, payload);
        }
        GetBlogResponse response = new GetBlogResponse();
        response.addValidationMessage(new ValidationMessage(EValidationMessage.INVALID_API));
        return response;
    }
}
