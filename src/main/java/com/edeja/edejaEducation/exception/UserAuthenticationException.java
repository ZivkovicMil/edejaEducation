package com.edeja.edejaEducation.exception;


import com.edeja.edejaEducation.Messages;

public class UserAuthenticationException extends Exception {
  private final Messages messages;

  public UserAuthenticationException(Messages messages) {
    super();
    this.messages = messages;
  }

  public Messages getMessages() {
    return messages;
  }
}
