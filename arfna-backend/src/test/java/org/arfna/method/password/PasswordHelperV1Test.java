package org.arfna.method.password;

import org.arfna.method.common.EValidationMessage;
import org.arfna.method.common.ValidationMessage;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PasswordHelperV1Test {

    @Test
    public void testPasswordAuthentication() {
        IPasswordHelper generator = new PasswordHelperV1();
        String myPassword = "myStrongP@55w0rd";
        String hashedPassword = generator.getEncryptedPassword(myPassword);
        assertTrue(generator.isPasswordValid(myPassword, hashedPassword));
    }

    @Test
    public void testFailedPasswordAuthentication() {
        IPasswordHelper generator = new PasswordHelperV1();
        String myPassword = "myStrongP@55w0rd";
        String hashedPassword = generator.getEncryptedPassword(myPassword);
        assertFalse(generator.isPasswordValid("dangIHopeImClose122", hashedPassword));
    }

    @Test
    public void testPasswordValidation() {
        String lowercasePassword = "mypasswordwithlotsofchars";
        IPasswordHelper helper = new PasswordHelperV1();
        List<ValidationMessage> messages = helper.validatePassword(lowercasePassword);
        assertEquals(3, messages.size());
        List<ValidationMessage> expectedMessages = Arrays.asList(
                new ValidationMessage(EValidationMessage.PASSWORD_NUMBER),
                new ValidationMessage(EValidationMessage.PASSWORD_SPECIAL_CHAR),
                new ValidationMessage(EValidationMessage.PASSWORD_ONE_CAPITAL_CHAR)
        );
        assertTrue(messages.containsAll(expectedMessages));
    }

    @Test
    public void testPasswordPassesValidation() {
        String lowercasePassword = "MyP@sswordHasSpecialChars123";
        IPasswordHelper helper = new PasswordHelperV1();
        List<ValidationMessage> messages = helper.validatePassword(lowercasePassword);
        assertTrue(messages.isEmpty());
    }

    @Test
    public void testPasswordNotLongEnough() {
        String strongPassword = "MyR3@11y";
        IPasswordHelper helper = new PasswordHelperV1();
        List<ValidationMessage> messages = helper.validatePassword(strongPassword);
        assertEquals(1, messages.size());
        assertTrue(messages.contains(new ValidationMessage(EValidationMessage.PASSWORD_NOT_LONG_ENOUGH)));
    }

}
