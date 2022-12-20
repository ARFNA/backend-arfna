package org.arfna.method.password;

import org.arfna.method.common.EValidationMessage;
import org.arfna.method.common.ValidationMessage;
import org.mindrot.jbcrypt.BCrypt;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class PasswordHelperV1 implements IPasswordHelper {

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

    @Override
    public List<ValidationMessage> validatePassword(String plainTextPassword) {
        List<ValidationMessage> messages = new ArrayList<>();
        if (!validatePasswordLength(plainTextPassword)) {
            messages.add(new ValidationMessage(EValidationMessage.PASSWORD_NOT_LONG_ENOUGH));
        } if (!validateOneLowerCaseCharacter(plainTextPassword)) {
            messages.add(new ValidationMessage(EValidationMessage.PASSWORD_ONE_LOWERCASE_CHAR));
        } if (!validateOneCapitalCharacter(plainTextPassword)) {
            messages.add(new ValidationMessage(EValidationMessage.PASSWORD_ONE_CAPITAL_CHAR));
        } if (!validateOneNumber(plainTextPassword)) {
            messages.add(new ValidationMessage(EValidationMessage.PASSWORD_NUMBER));
        } if (!validateOneSpecialCharacter(plainTextPassword)) {
            messages.add(new ValidationMessage(EValidationMessage.PASSWORD_SPECIAL_CHAR));
        }
        return messages;
    }

    private boolean validatePasswordLength(String plainTextPassword) {
        int length = 12;
        return plainTextPassword.length() >= length;
    }

    private boolean validateOneCapitalCharacter(String plainTextPassword) {
        String oneCapitalLetterRegex = "[A-Z]+";
        return Pattern.compile(oneCapitalLetterRegex).matcher(plainTextPassword).find();
    }

    private boolean validateOneLowerCaseCharacter(String plainTextPassword) {
        String oneLowerLetterRegex = "[a-z]+";
        return Pattern.compile(oneLowerLetterRegex).matcher(plainTextPassword).find();
    }

    private boolean validateOneNumber(String plainTextPassword) {
        String numberRegex = "[0-9]+";
        return Pattern.compile(numberRegex).matcher(plainTextPassword).find();
    }

    private boolean validateOneSpecialCharacter(String plainTextPassword) {
        String specialCharacter = "[!@#$%^&*()<>+]+";
        return Pattern.compile(specialCharacter).matcher(plainTextPassword).find();
    }

}
