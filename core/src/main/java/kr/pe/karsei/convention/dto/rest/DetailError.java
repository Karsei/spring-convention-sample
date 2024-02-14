package kr.pe.karsei.convention.dto.rest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 상세 내용이 함께 들어있는 오류를 나타냅니다.
 * @param <T> 리스트로 출력될 오류 상세 클래스
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DetailError<T> implements AdditionalError<T> {
    @Schema(description = "오류 코드", example = "V-0001-XXX")
    private String code;
    @Schema(description = "오류 메시지", example = "오류가 발생했습니다.")
    private String message;
    @Schema(description = "오류 상세")
    private List<T> details;
}
