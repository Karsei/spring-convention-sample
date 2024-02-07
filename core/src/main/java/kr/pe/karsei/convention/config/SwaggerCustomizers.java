package kr.pe.karsei.convention.config;

import kr.pe.karsei.convention.dto.rest.FilterOperator;
import kr.pe.karsei.convention.dto.rest.swagger.ConventionFilter;
import com.google.common.base.CaseFormat;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.SpecVersion;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.Parameter;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Pageable;
import org.springframework.web.method.HandlerMethod;

import java.util.*;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SwaggerCustomizers {
    public static Parameter parameterCustomize(SpringDataWebProperties webProperties,
                                               Parameter parameterModel,
                                               MethodParameter methodParameter) {
        if (parameterModel != null && isWithConventionFilter(methodParameter)) {
            String filterKey = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, parameterModel.getName());
            if (List.of(webProperties.getPageable().getPageParameter(), webProperties.getPageable().getSizeParameter(), webProperties.getSort().getSortParameter()).contains(filterKey))
                return parameterModel;

            Optional<String> operatorOp = Arrays.stream(FilterOperator.values())
                    .map(Enum::name)
                    .filter(name -> filterKey.endsWith(String.format("_%s", name.toLowerCase())))
                    .findFirst();
            if (operatorOp.isPresent()) {
                String lower = operatorOp.get().toLowerCase();
                parameterModel.setName(String.format("filter[%s][%s]", filterKey.substring(0, filterKey.indexOf("_" + lower)), lower));
            } else {
                throw new IllegalArgumentException(String.format("파라미터 '%s' (클래스: %s) 이름에서 Operator 를 찾을 수 없습니다.", filterKey, methodParameter.getDeclaringClass().getCanonicalName()));
            }
        }
        return parameterModel;
    }

    private static boolean isWithConventionFilter(MethodParameter methodParameter) {
        return methodParameter.getDeclaringClass().getAnnotation(ConventionFilter.class) != null ||
                methodParameter.getParameterAnnotation(ConventionFilter.class) != null;
    }

    public static Operation operationCustomize(SpringDataWebProperties webProperties,
                                               Operation operation,
                                               HandlerMethod handlerMethod) {
        if (operation != null) {
            Optional<java.lang.reflect.Parameter> pageable = Arrays.stream(handlerMethod.getMethod().getParameters())
                    .filter(parameter -> parameter.getType().equals(Pageable.class))
                    .findAny();
            if (pageable.isPresent()) {
                Optional<MethodParameter> filter = Arrays.stream(handlerMethod.getMethodParameters())
                        .filter(methodParameter -> methodParameter.hasParameterAnnotation(ConventionFilter.class))
                        .findAny();
                if (filter.isPresent()) {
                    // unlimit 추가
                    addPageUnlimitParameter(operation);

                    // 파라미터 이름, 설명 변환
                    operation.getParameters().forEach(parameter -> {
                        if (parameter.getName().equals(webProperties.getPageable().getPageParameter())) {
                            parameter.setName("page[offset]");
                            parameter.setDescription("페이지 오프셋 (0부터 시작)");
                        } else if (parameter.getName().equals(webProperties.getPageable().getSizeParameter())) {
                            parameter.setName("page[limit]");
                            parameter.setDescription("페이지 사이즈");
                        } else if (parameter.getName().equals(webProperties.getSort().getSortParameter())) {
                            parameter.setDescription("정렬. 조건 형식: 속성,(asc|desc). 기본값은 오름차순입니다. 여러 개의 정렬도 지원합니다.");
                        }
                    });
                }
            }
        }
        return operation;
    }

    private static void addPageUnlimitParameter(Operation operation) {
        Schema<Integer> schema = new Schema<>(SpecVersion.V30);
        schema.setType("boolean");
        schema.setExampleSetFlag(false);
        schema.types(new LinkedHashSet<>() {{ add("boolean"); }});
        Parameter unlimited = new Parameter();
        unlimited.setRequired(false);
        unlimited.setIn("query");
        unlimited.setSchema(schema);
        unlimited.setName("page[unlimit]");
        unlimited.setDescription("전체 리스트를 조회할지 여부. true 로 설정하면 offset 과 limit 은 무시됩니다.");
        unlimited.setExample("false");

        operation.setParameters(new ArrayList<>(operation.getParameters()) {{ add(unlimited); }});
    }
}
