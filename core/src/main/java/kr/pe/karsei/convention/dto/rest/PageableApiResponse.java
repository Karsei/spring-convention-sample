package kr.pe.karsei.convention.dto.rest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class PageableApiResponse<T> implements AdditionalApiResponse<Collection<T>> {
    @Schema(description = "결과 데이터")
    private final List<T> data;
    @Schema(description = "부가 정보")
    private final PageableMeta meta;

    public static <T> PageableApiResponse<T> of(Page<T> data) {
        return new PageableApiResponse<>(
                data.stream().collect(Collectors.toList()),
                PageableMeta.of(data.getTotalElements(), data.getPageable())
        );
    }

    public static <T, R> PageableApiResponse<T> of(Page<R> data,
                                                   Function<R, T> mappingFunction) {
        return new PageableApiResponse<>(
                data.stream().map(mappingFunction).collect(Collectors.toList()),
                PageableMeta.of(data.getTotalElements(), data.getPageable())
        );
    }
}
