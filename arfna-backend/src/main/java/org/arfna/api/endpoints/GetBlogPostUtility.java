package org.arfna.api.endpoints;

import org.arfna.api.version.EVersion;
import org.arfna.method.blog.GetBlogPayload;
import org.arfna.method.blog.GetBlogResponse;
import org.arfna.util.gson.GsonHelper;
import org.arfna.util.logger.ArfnaLogger;

public class GetBlogPostUtility {

    public GetBlogResponse getResponse(String inputPayload, EVersion version) {
        ArfnaLogger.debug(this.getClass(), "Version is: " + version.name());
        GetBlogPayload payload = GsonHelper.getGson().fromJson(inputPayload, GetBlogPayload.class);
        return version.getGetBlogApi().getResponse(payload, version);
    }

}
