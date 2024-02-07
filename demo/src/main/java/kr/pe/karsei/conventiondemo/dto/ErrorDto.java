package kr.pe.karsei.conventiondemo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ErrorDto {
    @Schema(description = "이름")
    private String name;
    @Schema(description = "인자")
    private List<String> arguments;
}
