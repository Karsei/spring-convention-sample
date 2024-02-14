package kr.pe.karsei.convention.dto.rest;

import io.swagger.v3.oas.annotations.media.Schema;

public interface Error {
    /**
     * 발생한 오류 코드를 나타냅니다.
     *
     * <p>오류 코드에는 규칙성과 의미를 부여할 것을 권장합니다. (예시: V-0001-XX)</p>
     * @return 오류 코드
     */
    @Schema(description = "오류 코드", example = "V-0001-XXX")
    String getCode();

    /**
     * 발생한 오류 메시지를 나타냅니다.
     *
     * <p>오류 메시지에는 발생한 오류와 제약사항에 대해 알기 쉽게 표현해야 하며 클라이언트가 정상적인 호출을 할 수 있도록 유도할 수 있어야 합니다.</p>
     * <p>다만, HTTP 5XX 계열 오류는 서버 정보를 내포하지 않기 위해 모호한 문구로 표현할 수 있습니다.</p>
     * @return 오류 메시지
     */
    @Schema(description = "오류 메시지", example = "오류가 발생했습니다.")
    String getMessage();
}
