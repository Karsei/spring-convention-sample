package kr.pe.karsei.convention.config;

import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.parameters.Parameter;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springdoc.core.customizers.ParameterCustomizer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.web.method.HandlerMethod;

@Configuration(proxyBeanMethods = false)
public class SwaggerCustomizer {
    @Configuration(proxyBeanMethods = false)
    @ConditionalOnProperty(name = "convention.swagger.enabled", havingValue = "true")
    @RequiredArgsConstructor
    public static class SwaggerRestConfig implements ParameterCustomizer {
        private final SpringDataWebProperties webProperties;

        @Override
        public Parameter customize(Parameter parameterModel, MethodParameter methodParameter) {
            return SwaggerCustomizers.parameterCustomize(webProperties, parameterModel, methodParameter);
        }
    }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnProperty(name = "convention.swagger.enabled", havingValue = "true")
    @RequiredArgsConstructor
    public static class SwaggerOperationCustomizer implements OperationCustomizer {
        private final SpringDataWebProperties webProperties;

        @Override
        public Operation customize(Operation operation, HandlerMethod handlerMethod) {
            return SwaggerCustomizers.operationCustomize(webProperties, operation, handlerMethod);
        }
    }
}
