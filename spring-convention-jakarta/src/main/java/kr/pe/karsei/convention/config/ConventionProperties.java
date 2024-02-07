package kr.pe.karsei.convention.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties("convention")
public class ConventionProperties {
    private final RestProperties rest = new RestProperties();
    private final SwaggerProperties swagger = new SwaggerProperties();

    @Getter
    @Setter
    public static class RestProperties {
        /**
         * 컨벤션 활성화 여부
         */
        private boolean enabled = true;
    }

    @Getter
    @Setter
    public static class SwaggerProperties {
        /**
         * Swagger 자동 변환 활성화 여부
         */
        private boolean enabled = true;
    }
}
