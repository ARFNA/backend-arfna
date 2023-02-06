package org.arfna.method.blog.images;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.RandomStringUtils;
import org.arfna.api.version.EVersion;
import org.arfna.database.entity.Post;
import org.arfna.database.entity.Subscriber;
import org.arfna.method.blog.images.gson.Thumbnail;
import org.arfna.method.common.EValidationMessage;
import org.arfna.method.common.ValidationMessage;
import org.arfna.method.password.middleware.ESubscriberRole;
import org.arfna.util.logger.ArfnaLogger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class ImageIdHelper {

    ImageIdResponse generateImageId(ImageIdPayload payload, EVersion version, Subscriber subscriber) {
        int subscriberId = subscriber.getId();
        int postId = payload.getPost().getId();
        String s3Path = this.generateId(subscriberId, postId);
        String generatedId = generateValidId(s3Path, version);
        return new ImageIdResponse().withImageId(this.generateId(subscriberId, postId, generatedId));
    }

    ImageIdResponse uploadImage(ImageIdPayload payload, EVersion version, Subscriber subscriber) {
        String base64Encoding = payload.getThumbnail().getBase64();
        // construct image file
        byte[] decoded = Base64.decodeBase64(base64Encoding);
        ImageIdResponse idResponse = this.generateImageId(payload, version, subscriber);
        String fullPath = idResponse + payload.getThumbnail().getExtension();
        File tempFile = new File(System.getProperty("user.dir") + File.separator + fullPath);
        tempFile.getParentFile().mkdirs();
        try (FileOutputStream outputStream = new FileOutputStream(tempFile)) {
            outputStream.write(decoded);
            // upload to s3
            version.getS3Util().uploadFile(tempFile.getAbsolutePath(), fullPath);
        } catch (IOException e) {
            ArfnaLogger.exception(this.getClass(), "Unable to recreate file from encoding", e);
            ImageIdResponse response = new ImageIdResponse();
            response.addValidationMessage(new ValidationMessage(EValidationMessage.IMAGE_UPLOAD_FAILURE));
            return response;
        } finally {
            // always delete file
            tempFile.delete();
        }
        return new ImageIdResponse().withImageId(fullPath);
    }

    ImageIdResponse getImage(ImageIdPayload payload, EVersion version) {
        String[] arr = payload.getS3Path().split("\\.");
        String extension = arr[arr.length - 1];
        try {
            byte[] bytes = version.getS3Util().downloadFile(payload.getS3Path());
            byte[] encodedBytes = Base64.encodeBase64(bytes);
            String jsonEncoding = new String(encodedBytes, StandardCharsets.US_ASCII);
            return new ImageIdResponse()
                    .withThumbnail(
                            new Thumbnail()
                                    .withBase64(jsonEncoding)
                                    .withExtension(extension)
                    );
        } catch (IOException e) {
            ArfnaLogger.exception(this.getClass(), "Unable to parse content", e);
            ImageIdResponse response = new ImageIdResponse();
            response.addValidationMessage(new ValidationMessage(EValidationMessage.IMAGE_PARSE_FAILURE));
            return response;
        }
    }

    boolean checkIfValidWritePermission(ImageIdPayload payload, EVersion version, Subscriber subscriber) {
        boolean subscriberAuthored = validateSubscriberAuthoredPost(payload.getPost(), version, subscriber);
        if (subscriberAuthored)
            return true;
        return validateSubscriberHasFullEdit(subscriber, version);
    }

    private boolean validateSubscriberAuthoredPost(Post p, EVersion version, Subscriber s) {
        ArfnaLogger.debug(this.getClass(), "Getting post from database");
        Post postFromDatabase = version.getDatabaseUtil().getPost(p.getId());
        return postFromDatabase.getAuthor().getId() == s.getId();
    }

    private boolean validateSubscriberHasFullEdit(Subscriber s, EVersion version) {
        return version.getMiddlewareHelper().isSubscriberAuthorized(Optional.of(s), ESubscriberRole.MAINT_ROLE);
    }

    String generateId(Object ... ids) {
        return Arrays.stream(ids).map(Object::toString).collect(Collectors.joining("ab/"));
    }

    String generateValidId(String s3Path, EVersion version) {
        ArfnaLogger.debug(this.getClass(), "Fetching all image files from S3");
        Set<String> fileNamesAlreadyTaken = version.getS3Util().getAllFilesInPath(s3Path);
        String generatedId = RandomStringUtils.randomAlphanumeric(15);
        while (fileNamesAlreadyTaken.contains(generatedId)) {
            generatedId = RandomStringUtils.randomAlphanumeric(15);
        }
        return generatedId;
    }
}
