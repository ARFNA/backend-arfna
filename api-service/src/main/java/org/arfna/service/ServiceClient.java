package org.arfna.service;

import org.arfna.api.ApiResponse;
import org.arfna.api.ArfnaUtility;
import org.arfna.api.Status;
import org.arfna.util.logger.ArfnaLogger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class ServiceClient {

    private String getDummyResponse(String json) {
        ArfnaUtility util = new ArfnaUtility();
        return util.getDummyResponse(json);
    }

    public ApiResponse execute(InputStream jsonStream, String endpoint) {
        ApiResponse apiResponse;
        try {
            String payload = generateJsonPayloadFromHttpStream(jsonStream);
            if (endpoint.contains("dummy")) {
                String methodResponse = getDummyResponse(payload);
                apiResponse = generateSuccessResponse(methodResponse);
            } else {
                ArfnaLogger.error(this.getClass(), endpoint + " is not a supported endpoint");
                apiResponse = generateFailureResponse(400, "Unsupported endpoint");
            }
        } catch (Exception e) {
            ArfnaLogger.exception(this.getClass(), "An exception occurred during execution of request", e);
            apiResponse = generateFailureResponse(400, e.getMessage());
        }
        return apiResponse;
    }

    private ApiResponse generateFailureResponse(int code, String reason) {
        Status status = new Status(code, reason);
        return new ApiResponse.ApiResponseBuilder().withStatus(status).build();
    }

    private ApiResponse generateSuccessResponse(String response) throws IOException {
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
