package org.arfna.method.blog.images;

import org.apache.commons.lang.RandomStringUtils;
import org.arfna.api.version.EVersion;
import org.arfna.database.entity.Post;
import org.arfna.database.entity.Subscriber;
import org.arfna.method.password.middleware.ESubscriberRole;
import org.arfna.util.logger.ArfnaLogger;

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

    public static void main(String[] args) {
        ImageIdHelper helper = new ImageIdHelper();
        System.out.println("Generated ID: " + helper.generateValidId("671ab/9", EVersion.V1));
        System.out.println("SUCCESS!");
    }
}
