package com.pablodev.documentworkspace.exceptions;

import com.pablodev.documentworkspace.constants.ErrorMessages;
import jakarta.persistence.EntityExistsException;

public class UserExistsException extends EntityExistsException {

    private static final String defaultMessage = ErrorMessages.USER_EXISTS_MESSAGE;

    public UserExistsException(String message) {
        super(message);
    }

    public UserExistsException() {
        super(defaultMessage);
    }
}
