package com.edeja.edejaEducation.exception;

import com.edeja.edejaEducation.Messages;

public class EmailValidationException extends Exception {

    private final Messages messages;

    public EmailValidationException(Messages messages) {
        super();
        this.messages = messages;
    }

    public Messages getMessages() {
        return messages;
    }
}
