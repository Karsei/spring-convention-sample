package kr.pe.karsei.conventiondemo.exception;

import kr.pe.karsei.conventiondemo.dto.ErrorDto;
import lombok.Getter;

import java.util.List;

@Getter
public class ValidationException extends AbstractApplicationRuntimeException {
    private final List<ErrorDto> errorList;

    public ValidationException(String message, List<ErrorDto> errorList) {
        super(SomeServiceErrorCode.FAILED_VALIDATE_WITH_SOME_ITEM.getErrorCode(), message);
        this.errorList = errorList;
    }
}
