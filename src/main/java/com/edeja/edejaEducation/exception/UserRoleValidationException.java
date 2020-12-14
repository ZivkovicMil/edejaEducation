package com.edeja.edejaEducation.exception;


import com.edeja.edejaEducation.Messages;

public class UserRoleValidationException extends Exception{

  private final Messages messages;

  public UserRoleValidationException(Messages messages) {
    super();
    this.messages = messages;
  }

  public Messages getMessages() {
    return messages;
  }

}
