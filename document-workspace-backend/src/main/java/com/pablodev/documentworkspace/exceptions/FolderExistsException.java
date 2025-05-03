package com.pablodev.documentworkspace.exceptions;

import com.pablodev.documentworkspace.constants.ErrorMessages;
import jakarta.persistence.EntityExistsException;

public class FolderExistsException extends EntityExistsException {

  private static final String defaultMessage = ErrorMessages.FOLDER_EXISTS_MESSAGE;

  public FolderExistsException(String message) {
    super(message);
  }

  public FolderExistsException() {
    super(defaultMessage);
  }

}
