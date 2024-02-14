package kr.pe.karsei.conventiondemo.config;

import kr.pe.karsei.convention.config.PageableArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration(proxyBeanMethods = false)
public class DemoWebMvcConfigurer implements WebMvcConfigurer {
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        // @LimitedPageSize 지원을 위해 추가
        resolvers.add(new PageableArgumentResolver());
    }
}
