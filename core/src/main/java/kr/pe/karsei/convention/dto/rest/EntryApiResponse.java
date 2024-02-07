package kr.pe.karsei.convention.dto.rest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EntryApiResponse<T> implements ApiResponse<T> {
    @Schema(description = "결과 데이터")
    private T data;

    public static <T> EntryApiResponse<T> of(T data) {
        return new EntryApiResponse<>(data);
    }
}
