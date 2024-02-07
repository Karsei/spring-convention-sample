package kr.pe.karsei.conventiondemo.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import kr.pe.karsei.convention.dto.rest.swagger.ConventionFilter;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TestDto {
    private String strVal;
    private Long longVal;
    private SomeObject objVal;
    private List<SomeObject> objListVal;

    @Getter
    @Setter
    @NoArgsConstructor
    @ConventionFilter
    public static class TestRequest {
        @Parameter(description = """
                문자열
                * 이렇게 필드 이름 맨 뒤에는 항상 `eq`, `like` 같은 operator 를 적어야 합니다.
                """, example = "GAZUUA")
        private String strValEq;
        @Parameter(description = "문자열 LIKE", example = "GAZUUA")
        private String strValLike;
        @Parameter(description = "문자열 IN")
        @ArraySchema(arraySchema = @Schema(example = "[\"test\", \"1234\"]"))
        private List<String> listValIn;
        @Parameter(description = "만기일 (시작)", example = "2024-01-16")
        private LocalDate expireDateGte;
        @Parameter(description = "만기일 (종료)", example = "2024-01-20")
        private LocalDate expireDateLt;
        @Parameter(description = "숫자", example = "1")
        private Long longValEq;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class TestLegacyRequest {
        @Parameter(description = "문자열", example = "GAZUUA")
        private String strVal;
        @Parameter(description = "문자열 LIKE", example = "GAZUUA")
        private String strValLike;
        @Parameter(description = "숫자", example = "1")
        private Long longValEq;
    }

    @Getter
    @AllArgsConstructor
    public static class SomeObject {
        private String strVal;
        private Long longVal;
    }
}
