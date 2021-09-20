package org.arfna.service;

public enum ESupportedEndpoints {

    DUMMY("dummy"),
    MUTATE_SUBSCRIBER("msubscriber");

    private String endpointName;

    ESupportedEndpoints(String endpointName) {
        this.endpointName = endpointName;
    }

    public String getEndpointName() {
        return endpointName;
    }
}
