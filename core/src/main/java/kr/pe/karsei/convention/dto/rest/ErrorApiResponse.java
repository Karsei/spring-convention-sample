package kr.pe.karsei.convention.dto.rest;

import io.swagger.v3.oas.annotations.media.Schema;

public interface ErrorApiResponse {
    /**
     * 오류를 나타냅니다.
     * @return 오류 정보
     */
    @Schema(description = "오류")
    Error getError();
}
