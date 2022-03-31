package org.arfna.service;

public enum ESupportedEndpoints {

    DUMMY("dummy"),
    MUTATE_SUBSCRIBER("msubscriber"),
    MUTATE_POST_TABLE("mpost"),
    GET_POST("gpost"),
    READ_SUBSCRIBER_COOKIE("rsubscriber"),
    ;

    private String endpointName;

    ESupportedEndpoints(String endpointName) {
        this.endpointName = endpointName;
    }

    public String getEndpointName() {
        return endpointName;
    }
}
