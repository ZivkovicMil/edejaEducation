package com.edeja.edejaEducation.config.security.jwt.key.exception;

public class UnableToLoadKeyException extends Exception {

  private static final long serialVersionUID = 4631418770031631794L;

  public UnableToLoadKeyException(String message, Exception e) {
    super(message, e);
  }
}
