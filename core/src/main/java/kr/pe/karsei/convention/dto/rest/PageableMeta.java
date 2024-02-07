package kr.pe.karsei.convention.dto.rest;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Pageable;

@Getter
@AllArgsConstructor
public class PageableMeta implements Meta {
    @Schema(description = "페이지 정보")
    private PageableMetaDetail page;

    @JsonIgnore
    private Pageable pageable;

    public static PageableMeta of(long total, Pageable pageable) {
        return new PageableMeta(
                new PageableMetaDetail(total, pageable.getPageSize(), pageable.getOffset()),
                pageable
        );
    }

    @Getter
    @AllArgsConstructor
    public static class PageableMetaDetail {
        @Schema(description = "전체 데이터 개수", example = "1")
        private long total;
        @Schema(description = "페이지 사이즈", example = "10")
        private int limit;
        @Schema(description = "페이지 커서 (0부터 시작)", example = "0")
        private long offset;
    }
}
