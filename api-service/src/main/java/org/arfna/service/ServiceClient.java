package org.arfna.service;

import org.arfna.api.ApiResponse;
import org.arfna.api.ArfnaUtility;
import org.arfna.api.Status;
import org.arfna.database.entity.Subscriber;
import org.arfna.method.common.MethodResponse;
import org.arfna.util.logger.ArfnaLogger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

public class ServiceClient {

    private MethodResponse getDummyResponse(String json) {
        ArfnaUtility util = new ArfnaUtility();
        return util.getDummyResponse(json);
    }

    private MethodResponse getMutateSubscriberResponse(String json) {
        ArfnaUtility util = new ArfnaUtility();
        return util.getMutateSubscriberResponse(json);
    }

    private MethodResponse getMutatePostTableResponse(String json, Optional<Subscriber> loggedInSubscriber) {
        ArfnaUtility util = new ArfnaUtility();
        return util.getMutatePostTableResponse(json, loggedInSubscriber);
    }

    private MethodResponse getPostsResponse(String json) {
        ArfnaUtility util = new ArfnaUtility();
        return util.getPostsResponse(json);
    }

    private MethodResponse getSubscriberCookieResponse(String json, Optional<Subscriber> loggedInSubscriber) {
        ArfnaUtility util = new ArfnaUtility();
        return util.getSubscriberCookieResponse(json, loggedInSubscriber);
    }

    private MethodResponse imageIdResponse(String json, Optional<Subscriber> loggedInSubscriber) {
        ArfnaUtility util = new ArfnaUtility();
        return util.imageIdResponse(json, loggedInSubscriber);
    }

    public ApiResponse execute(InputStream jsonStream, String endpoint, Optional<Subscriber> loggedInSubscriber) {
        ApiResponse apiResponse;
        try {
            String payload = generateJsonPayloadFromHttpStream(jsonStream);
            if (endpoint.contains(ESupportedEndpoints.DUMMY.getEndpointName())) {
                MethodResponse methodResponse = getDummyResponse(payload);
                apiResponse = generateResponse(methodResponse, false);
            } else if (endpoint.contains(ESupportedEndpoints.MUTATE_SUBSCRIBER.getEndpointName())) {
                MethodResponse methodResponse = getMutateSubscriberResponse(payload);
                apiResponse = generateResponse(methodResponse, false);
            } else if (endpoint.contains(ESupportedEndpoints.MUTATE_POST_TABLE.getEndpointName())) {
                MethodResponse methodResponse = getMutatePostTableResponse(payload, loggedInSubscriber);
                apiResponse = generateResponse(methodResponse, true);
            } else if (endpoint.contains(ESupportedEndpoints.GET_POST.getEndpointName())) {
                MethodResponse methodResponse = getPostsResponse(payload);
                apiResponse = generateResponse(methodResponse, false);
            } else if (endpoint.contains(ESupportedEndpoints.READ_SUBSCRIBER_COOKIE.getEndpointName())) {
                MethodResponse methodResponse = getSubscriberCookieResponse(payload, loggedInSubscriber);
                apiResponse = generateResponse(methodResponse, true);
            } else if (endpoint.contains(ESupportedEndpoints.IMAGE_ID.getEndpointName())) {
                MethodResponse methodResponse = imageIdResponse(payload, loggedInSubscriber);
                apiResponse = generateResponse(methodResponse, true);
            }
            else {
                ArfnaLogger.error(this.getClass(), endpoint + " is not a supported endpoint");
                apiResponse = generateFailureResponse(400, "Unsupported endpoint");
            }
        } catch (Exception e) {
            ArfnaLogger.exception(this.getClass(), "An exception occurred during execution of request", e);
            apiResponse = generateFailureResponse(400, e.getMessage());
        }
        return apiResponse;
    }

    private ApiResponse generateResponse(MethodResponse methodResponse, boolean requiresAuthCheck) {
        if (requiresAuthCheck && methodResponse.isUnauthorized()) {
            return generateNotAuthorizedResponse();
        } else if (!methodResponse.passedValidation()) {
            return generateValidationFailureResponse(methodResponse);
        }
        return generateSuccessResponse(methodResponse);
    }

    private ApiResponse generateNotAuthorizedResponse() {
        return generateFailureResponse(401, "User is not authorized to make API call");
    }

    private ApiResponse generateValidationFailureResponse(MethodResponse response) {
        return new ApiResponse.ApiResponseBuilder()
                .withStatus(new Status(412, "Validation checks not passed. Tables not modified."))
                .withResponse(response)
                .build();
    }

    private ApiResponse generateFailureResponse(int code, String reason) {
        Status status = new Status(code, reason);
        return new ApiResponse.ApiResponseBuilder().withStatus(status).build();
    }

    private ApiResponse generateSuccessResponse(MethodResponse response) {
        return new ApiResponse.ApiResponseBuilder()
                .withStatus(new Status(200, "OK"))
                .withResponse(response)
                .build();
    }

    private String generateJsonPayloadFromHttpStream(InputStream jsonStream) throws IOException {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        for (int length; (length = jsonStream.read(buffer)) != -1; ) {
            result.write(buffer, 0, length);
        }
        return result.toString(StandardCharsets.UTF_8.name());
    }

}
