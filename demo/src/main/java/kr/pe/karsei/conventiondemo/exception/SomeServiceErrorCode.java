package kr.pe.karsei.conventiondemo.exception;

import lombok.Getter;

@Getter
public enum SomeServiceErrorCode {
    NOT_FOUND("V-0001"),
    FAILED_VALIDATE_WITH_SOME_ITEM("V-0002"),
    ;

    private final String errorCode;

    SomeServiceErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
