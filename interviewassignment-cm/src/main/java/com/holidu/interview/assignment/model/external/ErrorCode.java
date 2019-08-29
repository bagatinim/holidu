package com.holidu.interview.assignment.model.external;

public enum ErrorCode {

    EMPTY_RESULT("Empty result returned from external service, no data to display."),
    UNEXPECTED_ERROR("An unforeseen error happened while processing the request."),
    ERROR_WHILE_FETCHING_DATA("Error while fetching data from an external source.");

    private final String errorMessage;

    ErrorCode(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return errorMessage;
    }

}
