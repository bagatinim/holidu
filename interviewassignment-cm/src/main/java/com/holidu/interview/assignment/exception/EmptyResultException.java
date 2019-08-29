package com.holidu.interview.assignment.exception;

import com.holidu.interview.assignment.model.external.ErrorCode;

/**
 * Thrown when the response from the external service is empty or in error.
 */
public class EmptyResultException extends BaseException {

    public EmptyResultException(ErrorCode errorCode) {
        super(errorCode);
    }

}
