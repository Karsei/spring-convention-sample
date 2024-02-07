package kr.pe.karsei.conventiondemo.exception;

public class NotFoundException extends AbstractApplicationRuntimeException {
    public NotFoundException(String message) {
        super(SomeServiceErrorCode.NOT_FOUND.getErrorCode(), message);
    }
}
