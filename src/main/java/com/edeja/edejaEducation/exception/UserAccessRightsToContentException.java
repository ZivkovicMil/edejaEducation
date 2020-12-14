package com.edeja.edejaEducation.exception;


import com.edeja.edejaEducation.Messages;

public class UserAccessRightsToContentException extends Exception {

  private final Messages messages;

  public UserAccessRightsToContentException(Messages messages) {
    super();
    this.messages = messages;
  }

  public Messages getMessages() {
    return messages;
  }

}
