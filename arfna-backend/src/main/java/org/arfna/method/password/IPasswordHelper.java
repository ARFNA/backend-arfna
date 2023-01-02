package org.arfna.method.password;

import org.arfna.method.common.ValidationMessage;

import java.util.List;

public interface IPasswordHelper {

    String getEncryptedPassword(String plainTextPassword);

    boolean isPasswordValid(String plainTextPassword, String hashedPassword);

    List<ValidationMessage> validatePassword(String plainTextPassword);

}
