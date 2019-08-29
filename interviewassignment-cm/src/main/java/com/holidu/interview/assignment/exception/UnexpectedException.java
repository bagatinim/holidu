package com.holidu.interview.assignment.exception;

import com.holidu.interview.assignment.model.external.ErrorCode;

/**
 * Exception used to encapsulate all unforeseen errors.
 */
public class UnexpectedException extends BaseException {

    public UnexpectedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
