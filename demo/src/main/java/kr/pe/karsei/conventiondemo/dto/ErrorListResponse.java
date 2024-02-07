package kr.pe.karsei.conventiondemo.dto;

import kr.pe.karsei.convention.dto.rest.AdditionalErrorApiResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Getter
public class ErrorListResponse<T> extends ErrorResponse implements AdditionalErrorApiResponse<T> {
    @Schema(description = "세부 오류")
    private final List<T> errorList;

    public ErrorListResponse(String errorCode,
                             String errorMessage,
                             List<T> errorList) {
        super(errorCode, errorMessage);
        this.errorList = errorList;
    }
}
