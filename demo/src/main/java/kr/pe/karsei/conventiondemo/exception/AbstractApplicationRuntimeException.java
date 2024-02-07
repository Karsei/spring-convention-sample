package kr.pe.karsei.conventiondemo.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public abstract class AbstractApplicationRuntimeException extends RuntimeException {
    private final String errorCode;

    public AbstractApplicationRuntimeException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
