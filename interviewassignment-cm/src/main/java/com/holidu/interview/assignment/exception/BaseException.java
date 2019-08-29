package com.holidu.interview.assignment.exception;

import com.holidu.interview.assignment.model.external.ErrorCode;

/**
 * Base exception for all custom exceptions.
 */
public class BaseException extends Exception {

    public BaseException(ErrorCode errorCode) {
        super(errorCode.toString());
    }

}
