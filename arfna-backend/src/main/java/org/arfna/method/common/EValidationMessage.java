package org.arfna.method.common;

public enum EValidationMessage {

    INVALID_API(0, "The API called does not exist"),
    SUBSCRIBER_ALREADY_EXISTS(1, "The subscriber is already subscribed and is registered"),
    SUBSCRIBER_NEEDS_TO_CREATE_PASSWORD(2, "The subscriber has an email registered, but no password"),
    PASSWORD_NOT_LONG_ENOUGH(3, "The password must be at least 12 characters long"),
    PASSWORD_ONE_CAPITAL_CHAR(4, "The password must contain at least 1 capital letter"),
    PASSWORD_ONE_LOWERCASE_CHAR(5, "The password must contain at least 1 lowercase letter"),
    PASSWORD_NUMBER(6, "The password must contain at least 1 number"),
    PASSWORD_SPECIAL_CHAR(7, "The password must contain at least one special characters - {!@#$%^&*()<>+}"),
    LOGIN_INCORRECT(8, "The login information provided is incorrect"),
    SUBSCRIBER_DOES_NOT_EXIST(9, "The subscriber does not exist in the database"),
    POST_NOT_BELONG_TO_SUBSCRIBER(10, "The given post does not belong to the subscriber"),
    POST_NOT_WRITTEN(11, "There was an error when creating the post in the database"),
    POST_DOES_NOT_EXIST(12, "The post with the given ID does not exist"),
    SUBSCRIBER_ALREADY_LOGGED_IN(13, "There is already a logged in subscriber. Sign out before logging in again."),
    SUBSCRIBER_NOT_LOGGED_IN(14, "There is not a signed in subscriber to accept terms of service."),
    INVALID_POST_PERMISSIONS(15, "The user attempting to adjust the post does not have valid permissions"),
    POST_DELETION_FAILED(16, "The request to delete the post has failed."),
    IMAGE_UPLOAD_FAILURE(17, "The image failed to upload"),
    IMAGE_PARSE_FAILURE(18, "Unable to parse input stream content"),
    ;

    private int code;
    private String message;

    EValidationMessage(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
