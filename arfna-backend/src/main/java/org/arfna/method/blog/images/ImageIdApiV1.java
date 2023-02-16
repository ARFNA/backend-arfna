package org.arfna.method.blog.images;

import org.arfna.api.version.EVersion;
import org.arfna.database.entity.Subscriber;
import org.arfna.method.common.EValidationMessage;
import org.arfna.method.common.ValidationMessage;
import org.arfna.method.password.middleware.ESubscriberRole;
import org.arfna.util.logger.ArfnaLogger;

import java.util.Optional;

public class ImageIdApiV1 implements IImageIdApi {

    @Override
    public ImageIdResponse getResponse(ImageIdPayload payload, EVersion version, Subscriber subscriber) {
        EImageRequest requestType = payload.getRequestType();
        if (requestType == EImageRequest.GENERATE_ID) {
            ImageIdHelper helper = new ImageIdHelper();
            ImageIdResponse response = checkPermissions(payload, version, subscriber, helper);
            if (response != null) return response;
            ArfnaLogger.debug(this.getClass(), "Generating unique id for image");
            return helper.generateImageId(payload, version, subscriber);
        } if (requestType == EImageRequest.UPLOAD_IMAGE) {
            ImageIdHelper helper = new ImageIdHelper();
            ImageIdResponse response = checkPermissions(payload, version, subscriber, helper);
            if (response != null) return response;
            ArfnaLogger.debug(this.getClass(), "Uploading thumbnail");
            return helper.uploadImage(payload, version, subscriber);
        } if (requestType == EImageRequest.DOWNLOAD_IMAGE) {
            ImageIdHelper helper = new ImageIdHelper();
            ArfnaLogger.debug(this.getClass(), "Downloading thumbnail");
            return helper.getImage(payload, version);
        }
        ImageIdResponse response = new ImageIdResponse();
        response.addValidationMessage(new ValidationMessage(EValidationMessage.INVALID_API));
        return response;
    }

    private ImageIdResponse checkPermissions(ImageIdPayload payload, EVersion version, Subscriber subscriber, ImageIdHelper helper) {
        ArfnaLogger.debug(this.getClass(), "Checking permissions for ID generation");
        if (!helper.checkIfValidWritePermission(payload, version, subscriber)) {
            ImageIdResponse response = new ImageIdResponse();
            response.setUnauthorized();
            return response;
        }
        return null;
    }

}
