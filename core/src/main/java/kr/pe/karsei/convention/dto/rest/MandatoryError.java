package kr.pe.karsei.convention.dto.rest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 기본 오류 내용이 포함된 오류를 나타냅니다.
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MandatoryError implements Error {
    @Schema(description = "오류 코드", example = "V-0001-XXX")
    private String code;
    @Schema(description = "오류 메시지", example = "오류가 발생했습니다.")
    private String message;
}
