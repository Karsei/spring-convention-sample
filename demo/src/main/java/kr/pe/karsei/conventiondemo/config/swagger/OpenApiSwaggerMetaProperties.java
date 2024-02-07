package kr.pe.karsei.conventiondemo.config.swagger;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("springdoc.meta")
@Getter
@Setter
public class OpenApiSwaggerMetaProperties {
    /**
     * 제목
     */
    private String title;

    /**
     * 설명
     */
    private String description;

    /**
     * 버전
     */
    private String version;
}
