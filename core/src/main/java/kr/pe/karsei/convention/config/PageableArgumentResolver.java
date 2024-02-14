package kr.pe.karsei.convention.config;

import kr.pe.karsei.convention.dto.rest.LimitedPageSize;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

public class PageableArgumentResolver extends PageableHandlerMethodArgumentResolver {
    @Override
    public Pageable resolveArgument(MethodParameter methodParameter,
                                    ModelAndViewContainer mavContainer,
                                    NativeWebRequest webRequest,
                                    WebDataBinderFactory binderFactory) {
        final Pageable pageable = super.resolveArgument(methodParameter, mavContainer, webRequest, binderFactory);

        final LimitedPageSize pageSizeAnnotation = methodParameter.getParameterAnnotation(LimitedPageSize.class);
        if (pageSizeAnnotation != null) {
            final int maxSize = pageSizeAnnotation.maxSize();
            // 지정된 최대 사이즈보다 더 넘어서면 지정된 최대 사이즈로 고정한다.
            if (pageable.getPageSize() > maxSize) {
                return PageRequest.of(pageable.getPageNumber(), maxSize, pageable.getSort());
            }
        }

        return pageable;
    }
}
