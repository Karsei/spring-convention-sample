package kr.pe.karsei.convention.config;

import kr.pe.karsei.convention.dto.rest.swagger.ConventionFilter;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.parameters.Parameter;
import org.junit.jupiter.api.Test;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.method.HandlerMethod;

import java.lang.reflect.Method;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class SwaggerCustomizerTest {
    private final SpringDataWebProperties webProperties = new SpringDataWebProperties();

    @Test
    void testParameterCustomizerFilteringTest() throws NoSuchMethodException {
        // given
        Parameter parameter = new Parameter();
        parameter.setName("someStringEq");
        MethodParameter methodParameter = new MethodParameter(SomeDto.class.getMethod("getSomeStringEq"), -1);

        // when
        SwaggerCustomizer.SwaggerRestConfig config = new SwaggerCustomizer.SwaggerRestConfig(webProperties);
        Parameter customized = config.customize(parameter, methodParameter);

        // then
        assertAll(
                () -> assertThat(customized).isNotNull(),
                () -> assertThat(customized.getName()).isEqualTo("filter[some_string][eq]")
        );
    }

    @Test
    void testParameterCustomizerFilteringWithNotFoundOperatorTest() throws NoSuchMethodException {
        // given
        Parameter parameter = new Parameter();
        parameter.setName("someString");
        MethodParameter methodParameter = new MethodParameter(SomeWrongDto.class.getMethod("getSomeString"), -1);

        // when & then
        SwaggerCustomizer.SwaggerRestConfig config = new SwaggerCustomizer.SwaggerRestConfig(webProperties);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> config.customize(parameter, methodParameter));
        assertAll(
                () -> assertThat(exception).isNotNull(),
                () -> assertThat(exception.getMessage()).isEqualTo("파라미터 'some_string' (클래스: kr.pe.karsei.convention.config.SwaggerCustomizerTest.SomeWrongDto) 이름에서 Operator 를 찾을 수 없습니다.")
        );
    }

    @Test
    void testParameterCustomizerFilteringWithLegacyTest() throws NoSuchMethodException {
        // given
        Parameter parameter = new Parameter();
        parameter.setName("someString");
        MethodParameter methodParameter = new MethodParameter(SomeLegacyDto.class.getMethod("getSomeString"), -1);

        // when
        SwaggerCustomizer.SwaggerRestConfig config = new SwaggerCustomizer.SwaggerRestConfig(webProperties);
        Parameter customized = config.customize(parameter, methodParameter);

        // then
        assertAll(
                () -> assertThat(customized).isNotNull(),
                () -> assertThat(customized.getName()).isEqualTo("someString")
        );
    }

    @ConventionFilter
    public static class SomeDto {
        @io.swagger.v3.oas.annotations.Parameter(description = "테스트")
        private String someStringEq;

        public String getSomeStringEq() {
            return someStringEq;
        }
    }

    @ConventionFilter
    public static class SomeWrongDto {
        @io.swagger.v3.oas.annotations.Parameter(description = "테스트")
        private String someString;

        public String getSomeString() {
            return someString;
        }
    }

    public static class SomeLegacyDto {
        @io.swagger.v3.oas.annotations.Parameter(description = "테스트")
        private String someString;

        public String getSomeString() {
            return someString;
        }
    }

    @Test
    void testOperationCustomizerFilteringTest() throws NoSuchMethodException {
        // given
        Parameter parameterPage = new Parameter();
        parameterPage.setName("page");
        Parameter parameterSize = new Parameter();
        parameterSize.setName("size");
        Parameter parameterSort = new Parameter();
        parameterSort.setName("sort");
        Operation operation = new Operation();
        operation.setParameters(List.of(parameterPage, parameterSize, parameterSort));

        // when
        SomeController controller = new SomeController();
        Method method = SomeController.class.getMethod("someHandler", Pageable.class);
        HandlerMethod handlerMethod = new HandlerMethod(controller, method);
        SwaggerCustomizer.SwaggerOperationCustomizer config = new SwaggerCustomizer.SwaggerOperationCustomizer(webProperties);
        Operation customized = config.customize(operation, handlerMethod);

        // result
        assertThat(customized).isNotNull();
        assertThat(customized.getParameters()).hasSize(3);
        assertThat(customized.getParameters()).anyMatch(parameter -> parameter.getName().equals("page[offset]"));
        assertThat(customized.getParameters()).anyMatch(parameter -> parameter.getName().equals("page[limit]"));
        assertThat(customized.getParameters()).anyMatch(parameter -> parameter.getName().equals("sort"));
    }

    @Test
    void testOperationCustomizerFilteringWithNotConventionFilterTest() throws NoSuchMethodException {
        // given
        Parameter parameterPage = new Parameter();
        parameterPage.setName("page");
        Parameter parameterSize = new Parameter();
        parameterSize.setName("size");
        Parameter parameterSort = new Parameter();
        parameterSort.setName("sort");
        Operation operation = new Operation();
        operation.setParameters(List.of(parameterPage, parameterSize, parameterSort));

        // when
        SomeLegacyController controller = new SomeLegacyController();
        Method method = SomeLegacyController.class.getMethod("someHandler", Pageable.class);
        HandlerMethod handlerMethod = new HandlerMethod(controller, method);
        SwaggerCustomizer.SwaggerOperationCustomizer config = new SwaggerCustomizer.SwaggerOperationCustomizer(webProperties);
        Operation customized = config.customize(operation, handlerMethod);

        // result
        assertThat(customized).isNotNull();
        assertThat(customized.getParameters()).hasSize(3);
        assertThat(customized.getParameters()).anyMatch(parameter -> parameter.getName().equals("page"));
        assertThat(customized.getParameters()).anyMatch(parameter -> parameter.getName().equals("size"));
        assertThat(customized.getParameters()).anyMatch(parameter -> parameter.getName().equals("sort"));
    }

    public static class SomeController {
        @io.swagger.v3.oas.annotations.Operation
        @GetMapping
        public String someHandler(@ParameterObject @ConventionFilter Pageable pageable) {
            return "test";
        }
    }

    public static class SomeLegacyController {
        @io.swagger.v3.oas.annotations.Operation
        @GetMapping
        public String someHandler(@ParameterObject Pageable pageable) {
            return "test";
        }
    }
}