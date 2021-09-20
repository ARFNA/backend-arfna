package org.arfna.method.login;

public interface IPasswordGenerator {

    String getEncryptedPassword(String plainTextPassword);

    boolean isPasswordValid(String plainTextPassword, String hashedPassword);

}
