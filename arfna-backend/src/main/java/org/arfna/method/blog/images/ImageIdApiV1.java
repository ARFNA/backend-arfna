package org.arfna.method.blog.images;

import org.arfna.api.version.EVersion;
import org.arfna.database.entity.Subscriber;
import org.arfna.method.common.EValidationMessage;
import org.arfna.method.common.ValidationMessage;
import org.arfna.util.logger.ArfnaLogger;

public class ImageIdApiV1 implements IImageIdApi {

    @Override
    public ImageIdResponse getResponse(ImageIdPayload payload, EVersion version, Subscriber subscriber) {
        EImageRequest requestType = payload.getRequestType();
        if (requestType == EImageRequest.GENERATE_ID) {
            ImageIdHelper helper = new ImageIdHelper();
//            ArfnaLogger.debug(this.getClass(), "Checking permissions for ID generation");
//            if (!helper.checkIfValidWritePermission(payload, version, subscriber)) {
//                ImageIdResponse response = new ImageIdResponse();
//                response.setUnauthorized();
//                return response;
//            }
            ArfnaLogger.debug(this.getClass(), "Generating unique id for image");
            return helper.generateImageId(payload, version, subscriber);
        }
        ImageIdResponse response = new ImageIdResponse();
        response.addValidationMessage(new ValidationMessage(EValidationMessage.INVALID_API));
        return response;
    }

}
