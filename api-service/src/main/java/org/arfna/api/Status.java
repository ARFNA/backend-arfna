package org.arfna.api;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class Status implements Serializable {
    private static final long serialVersionUID = -2102699015648677435L;

    @Expose private int code;
    @Expose private String message;

    public Status(int code) {
        this(code, null);
    }

    public Status(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
