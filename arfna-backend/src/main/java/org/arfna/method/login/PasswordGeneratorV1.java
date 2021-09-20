package org.arfna.method.login;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordGeneratorV1 implements IPasswordGenerator {

    private static final int NUM_ROUNDS_FOR_SALT = 14;

    @Override
    public String getEncryptedPassword(String plainTextPassword) {
        String salt = BCrypt.gensalt(NUM_ROUNDS_FOR_SALT);
        return BCrypt.hashpw(plainTextPassword, salt);
    }

    @Override
    public boolean isPasswordValid(String plainTextPassword, String hashedPassword) {
        return BCrypt.checkpw(plainTextPassword, hashedPassword);
    }
}
