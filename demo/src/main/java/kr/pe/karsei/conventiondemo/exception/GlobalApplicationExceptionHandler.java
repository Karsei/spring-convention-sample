package kr.pe.karsei.conventiondemo.exception;

import kr.pe.karsei.conventiondemo.dto.ErrorDto;
import kr.pe.karsei.conventiondemo.dto.ErrorListResponse;
import kr.pe.karsei.conventiondemo.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalApplicationExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public static ErrorResponse handleNotFoundException(NotFoundException exception) {
        return new ErrorResponse(exception.getErrorCode(), exception.getMessage());
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public static ErrorListResponse<ErrorDto> handleValidationException(ValidationException exception) {
        return new ErrorListResponse<>(
                exception.getErrorCode(),
                exception.getMessage(),
                exception.getErrorList()
        );
    }
}
