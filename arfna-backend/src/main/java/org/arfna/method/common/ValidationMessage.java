package org.arfna.method.common;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class ValidationMessage implements Serializable {
    private static final long serialVersionUID = 6344052637969029985L;

    @Expose private int code;
    @Expose private String message;

    public ValidationMessage(EValidationMessage msg) {
        this.code = msg.getCode();
        this.message = msg.getMessage();
    }

    private ValidationMessage(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ValidationMessage createCustomValidation(int code, String message) {
        return new ValidationMessage(code, message);
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public boolean equals(Object o) {
        if (o instanceof ValidationMessage) {
            ValidationMessage other = (ValidationMessage) o;
            return other.getCode() == this.getCode();
        }
        return false;
    }

}
