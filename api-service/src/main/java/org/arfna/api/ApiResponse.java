package org.arfna.api;

import com.google.gson.annotations.Expose;
import org.arfna.method.common.MethodResponse;

import java.io.Serializable;

public class ApiResponse implements Serializable {
    private static final long serialVersionUID = -8870025619063410219L;

    @Expose private Status status;
    @Expose private MethodResponse response;

    private ApiResponse(Status status, MethodResponse response) {
        this.status = status;
        this.response = response;
    }

    public Status getStatus() {
        return status;
    }

    public MethodResponse getResponse() {
        return response;
    }

    public static class ApiResponseBuilder {

        private Status status;
        private MethodResponse response;

        public ApiResponseBuilder withStatus(Status status) {
            this.status = status;
            return this;
        }

        public ApiResponseBuilder withResponse(MethodResponse response) {
            this.response = response;
            return this;
        }

        public ApiResponse build() {
            return new ApiResponse(this.status, this.response);
        }
    }
}
