package org.arfna.api;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class ApiResponse implements Serializable {
    private static final long serialVersionUID = -8870025619063410219L;

    @Expose private Status status;
    @Expose private Object response;

    private ApiResponse(Status status, Object response) {
        this.status = status;
        this.response = response;
    }

    public static class ApiResponseBuilder {

        private Status status;
        private Object response;

        public ApiResponseBuilder withStatus(Status status) {
            this.status = status;
            return this;
        }

        public ApiResponseBuilder withResponse(Object response) {
            this.response = response;
            return this;
        }

        public ApiResponse build() {
            return new ApiResponse(this.status, this.response);
        }
    }
}
