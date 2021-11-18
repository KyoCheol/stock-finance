package com.board.seochu.finance.exception.custom;

public class DuplicatedException2 extends RuntimeException {

    public DuplicatedException2(String msg, Throwable t) {
        super(msg, t);
    }

    public DuplicatedException2(String msg) {
        super(msg);
    }

    public DuplicatedException2() {
        super();
    }
}
