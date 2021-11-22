package com.board.seochu.finance.exception;

public class EmailSendException extends RuntimeException {

    public EmailSendException(String msg, Throwable t) {
        super(msg, t);
    }

    public EmailSendException(String msg) {
        super(msg);
    }

    public EmailSendException() {
        super();
    }

}
