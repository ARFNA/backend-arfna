package org.arfna.method.login;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PasswordGeneratorV1Test {

    @Test
    public void testPasswordAuthentication() {
        IPasswordGenerator generator = new PasswordGeneratorV1();
        String myPassword = "myStrongP@55w0rd";
        String hashedPassword = generator.getEncryptedPassword(myPassword);
        assertTrue(generator.isPasswordValid(myPassword, hashedPassword));
    }

    @Test
    public void testFailedPasswordAuthentication() {
        IPasswordGenerator generator = new PasswordGeneratorV1();
        String myPassword = "myStrongP@55w0rd";
        String hashedPassword = generator.getEncryptedPassword(myPassword);
        assertFalse(generator.isPasswordValid("dangIHopeImClose122", hashedPassword));
    }

}
