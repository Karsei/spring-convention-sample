package kr.pe.karsei.convention.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.IOException;
import java.util.*;

@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(name = "convention.rest.enabled", havingValue = "true")
public class ConventionWebMvcConfigResolver implements WebMvcConfigurer {
    @Bean
    public OncePerRequestFilter snakeCaseConverterFilter(SpringDataWebProperties webProperties) {
        return new OncePerRequestFilter() {
            @Override
            protected void doFilterInternal(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain filterChain) throws ServletException, IOException {
                final Map<String, String[]> parameters = FilterWebParameterResolver.conventionFilter(new CompatibleServletRequest() {
                    @Override
                    public Map<String, String[]> getParameterMap() {
                        return request.getParameterMap();
                    }

                    @Override
                    public String[] getParameterValues(String parameterName) {
                        return request.getParameterValues(parameterName);
                    }
                }, webProperties);

                filterChain.doFilter(new HttpServletRequestWrapper(request) {
                    @Override
                    public String getParameter(String name){
                        return parameters.containsKey(name) ? parameters.get(name)[0] : null;
                    }

                    @Override
                    public Enumeration<String> getParameterNames(){
                        return Collections.enumeration(parameters.keySet());
                    }

                    @Override
                    public String[] getParameterValues(String name){
                        return parameters.get(name);
                    }

                    @Override
                    public Map<String, String[]> getParameterMap(){
                        return parameters;
                    }
                }, response);
            }
        };
    }
}
