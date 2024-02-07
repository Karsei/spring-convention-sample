package kr.pe.karsei.conventiondemo.config.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;

public class OpenApiSwaggerConfiguration {
    @Bean
    public OpenApiSwaggerMetaProperties openApiSwaggerMetaProperties() {
        return new OpenApiSwaggerMetaProperties();
    }

    @Bean
    public OpenAPI customOpenAPI(OpenApiSwaggerMetaProperties openApiSwaggerMetaProperties) {
        OpenAPI openAPI = new OpenAPI();
        openAPI.info(new Info()
                        .title(openApiSwaggerMetaProperties.getTitle())
                        .description(openApiSwaggerMetaProperties.getDescription())
                        .version(openApiSwaggerMetaProperties.getVersion()))
                .addServersItem(new Server().url("/"));
        return openAPI;
    }
}
