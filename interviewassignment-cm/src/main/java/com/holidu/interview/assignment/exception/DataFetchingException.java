package com.holidu.interview.assignment.exception;

import com.holidu.interview.assignment.model.external.ErrorCode;

/**
 * Thrown when there is a failure in fetching data from external sources.
 */
public class DataFetchingException extends BaseException {

    public DataFetchingException(ErrorCode errorCode) {
        super(errorCode);
    }

}
