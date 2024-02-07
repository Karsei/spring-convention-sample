package kr.pe.karsei.conventiondemo.dto;

import kr.pe.karsei.convention.dto.rest.ErrorApiResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ErrorResponse implements ErrorApiResponse {
    @Schema(description = "오류 코드")
    private final String errorCode;
    @Schema(description = "오류 메시지")
    private final String errorMessage;
}
