package kr.pe.karsei.conventiondemo.exception;

import kr.pe.karsei.convention.dto.rest.DetailError;
import kr.pe.karsei.convention.dto.rest.DetailErrorApiResponse;
import kr.pe.karsei.convention.dto.rest.MandatoryError;
import kr.pe.karsei.convention.dto.rest.MandatoryErrorApiResponse;
import kr.pe.karsei.conventiondemo.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalApplicationExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public static MandatoryErrorApiResponse handleNotFoundException(NotFoundException exception) {
        return new MandatoryErrorApiResponse(new MandatoryError(
                exception.getErrorCode(), exception.getMessage()
        ));
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public static DetailErrorApiResponse<ErrorDto> handleValidationException(ValidationException exception) {
        return new DetailErrorApiResponse<>(new DetailError<>(
                exception.getErrorCode(),
                exception.getMessage(),
                exception.getErrorList()
        ));
    }
}
