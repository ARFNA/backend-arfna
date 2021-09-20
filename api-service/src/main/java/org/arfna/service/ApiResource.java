package org.arfna.service;

import org.glassfish.jersey.server.ResourceConfig;

public class ApiResource {

    public ApiResource() {
        new ResourceConfig().packages("org.arfna");
    }

}
