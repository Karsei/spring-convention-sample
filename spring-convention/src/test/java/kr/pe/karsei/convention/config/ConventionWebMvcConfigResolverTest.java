package kr.pe.karsei.convention.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.ServletException;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ConventionWebMvcConfigResolverTest {
    private SpringDataWebProperties webProperties;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private MockFilterChain chain;

    @BeforeEach
    void setup() {
        this.webProperties = new SpringDataWebProperties();
        this.request = new MockHttpServletRequest();
        this.response = new MockHttpServletResponse();
        this.chain = new MockFilterChain();
    }

    @Test
    void camelCaseTest() throws ServletException, IOException {
        OncePerRequestFilter filter = new ConventionWebMvcConfigResolver().snakeCaseConverterFilter(this.webProperties);
        this.request.addParameter("userId", "leejy");

        filter.doFilter(this.request, this.response, this.chain);
        assertAll(
                () -> assertThat(this.chain.getRequest().getParameterMap()).isNotNull(),
                () -> assertThat(this.chain.getRequest().getParameterMap().keySet().size()).isEqualTo(1),
                () -> assertThat(this.chain.getRequest().getParameterMap().get("userId")).isNotNull(),
                () -> assertThat(this.chain.getRequest().getParameterMap().get("userId")).isNotEmpty(),
                () -> assertThat(this.chain.getRequest().getParameterMap().get("userId").length).isEqualTo(1),
                () -> assertThat(this.chain.getRequest().getParameterMap().get("userId")[0]).isEqualTo("leejy")
        );
    }

    @Test
    void pageOriginTest() throws ServletException, IOException {
        OncePerRequestFilter filter = new ConventionWebMvcConfigResolver().snakeCaseConverterFilter(this.webProperties);
        this.request.addParameter("page", "99");
        this.request.addParameter("size", "12");

        filter.doFilter(this.request, this.response, this.chain);
        assertAll(
                () -> assertThat(this.chain.getRequest().getParameterMap()).isNotNull(),
                () -> assertThat(this.chain.getRequest().getParameterMap().keySet().size()).isEqualTo(2),
                () -> assertThat(this.chain.getRequest().getParameterMap().get("page")).isNotNull(),
                () -> assertThat(this.chain.getRequest().getParameterMap().get("page")).isNotEmpty(),
                () -> assertThat(this.chain.getRequest().getParameterMap().get("page").length).isEqualTo(1),
                () -> assertThat(this.chain.getRequest().getParameterMap().get("page")[0]).isEqualTo("99"),
                () -> assertThat(this.chain.getRequest().getParameterMap().get("size")).isNotNull(),
                () -> assertThat(this.chain.getRequest().getParameterMap().get("size")).isNotEmpty(),
                () -> assertThat(this.chain.getRequest().getParameterMap().get("size").length).isEqualTo(1),
                () -> assertThat(this.chain.getRequest().getParameterMap().get("size")[0]).isEqualTo("12")
        );
    }

    @Test
    void pageFilterTest() throws ServletException, IOException {
        OncePerRequestFilter filter = new ConventionWebMvcConfigResolver().snakeCaseConverterFilter(this.webProperties);
        this.request.addParameter("page[offset]", "99");
        this.request.addParameter("page[limit]", "12");

        filter.doFilter(this.request, this.response, this.chain);
        assertAll(
                () -> assertThat(this.chain.getRequest().getParameterMap()).isNotNull(),
                () -> assertThat(this.chain.getRequest().getParameterMap().get("page")).isNotNull(),
                () -> assertThat(this.chain.getRequest().getParameterMap().get("page")).isNotEmpty(),
                () -> assertThat(this.chain.getRequest().getParameterMap().get("page").length).isEqualTo(1),
                () -> assertThat(this.chain.getRequest().getParameterMap().get("page")[0]).isEqualTo("99"),
                () -> assertThat(this.chain.getRequest().getParameterMap().get("size")).isNotNull(),
                () -> assertThat(this.chain.getRequest().getParameterMap().get("size")).isNotEmpty(),
                () -> assertThat(this.chain.getRequest().getParameterMap().get("size").length).isEqualTo(1),
                () -> assertThat(this.chain.getRequest().getParameterMap().get("size")[0]).isEqualTo("12"),
                () -> assertThat(this.chain.getRequest().getParameterMap().get("page[offset]")).isNull(),
                () -> assertThat(this.chain.getRequest().getParameterMap().get("page[limit]")).isNull()
        );
    }

    @Test
    void sortFilterWithIndexTest() throws ServletException, IOException {
        OncePerRequestFilter filter = new ConventionWebMvcConfigResolver().snakeCaseConverterFilter(this.webProperties);
        this.request.addParameter("sort[0][leejy]", "asc");
        this.request.addParameter("sort[1][some]", "desc");

        filter.doFilter(this.request, this.response, this.chain);
        assertAll(
                () -> assertThat(this.chain.getRequest().getParameterMap()).isNotNull(),
                () -> assertThat(this.chain.getRequest().getParameterMap().get("sort")).isNotNull(),
                () -> assertThat(this.chain.getRequest().getParameterMap().get("sort")).isNotEmpty(),
                () -> assertThat(this.chain.getRequest().getParameterMap().get("sort").length).isEqualTo(2),
                () -> assertThat(this.chain.getRequest().getParameterMap().get("sort")).contains("some,desc", "leejy,asc"),
                () -> assertThat(this.chain.getRequest().getParameterMap().get("sort")[0]).isEqualTo("leejy,asc"),
                () -> assertThat(this.chain.getRequest().getParameterMap().get("sort[0][leejy]")).isNull(),
                () -> assertThat(this.chain.getRequest().getParameterMap().get("sort[1][some]")).isNull()
        );
    }

    @Test
    void sortFilterTest() throws ServletException, IOException {
        OncePerRequestFilter filter = new ConventionWebMvcConfigResolver().snakeCaseConverterFilter(this.webProperties);
        this.request.addParameter("sort[leejy]", "aSc");
        this.request.addParameter("sort[some]", "dEsc");

        filter.doFilter(this.request, this.response, this.chain);
        assertAll(
                () -> assertThat(this.chain.getRequest().getParameterMap()).isNotNull(),
                () -> assertThat(this.chain.getRequest().getParameterMap().get("sort")).isNotNull(),
                () -> assertThat(this.chain.getRequest().getParameterMap().get("sort")).isNotEmpty(),
                () -> assertThat(this.chain.getRequest().getParameterMap().get("sort").length).isEqualTo(2),
                () -> assertThat(this.chain.getRequest().getParameterMap().get("sort")).contains("some,desc", "leejy,asc"),
                () -> assertThat(this.chain.getRequest().getParameterMap().get("sort[leejy]")).isNull(),
                () -> assertThat(this.chain.getRequest().getParameterMap().get("sort[some]")).isNull()
        );
    }

    @Test
    void sortFilterBlankTest() throws ServletException, IOException {
        OncePerRequestFilter filter = new ConventionWebMvcConfigResolver().snakeCaseConverterFilter(this.webProperties);
        this.request.addParameter("sort[leejy]", "");
        this.request.addParameter("sort[some]", "");

        filter.doFilter(this.request, this.response, this.chain);
        assertAll(
                () -> assertThat(this.chain.getRequest().getParameterMap()).isNotNull(),
                () -> assertThat(this.chain.getRequest().getParameterMap().get("sort")).isNull(),
                () -> assertThat(this.chain.getRequest().getParameterMap().get("sort[leejy]")).isNull(),
                () -> assertThat(this.chain.getRequest().getParameterMap().get("sort[some]")).isNull()
        );
    }

    @Test
    void filterWithNameOnlyTest() throws ServletException, IOException {
        OncePerRequestFilter filter = new ConventionWebMvcConfigResolver().snakeCaseConverterFilter(this.webProperties);
        this.request.addParameter("filter[user_id]", "leejy");

        filter.doFilter(this.request, this.response, this.chain);
        assertAll(
                () -> assertThat(this.chain.getRequest().getParameterMap()).isNotNull(),
                () -> assertThat(this.chain.getRequest().getParameterMap().get("userId")).isNull(),
                () -> assertThat(this.chain.getRequest().getParameterMap().get("user_id")).isNull(),
                () -> assertThat(this.chain.getRequest().getParameterMap().get("userIdEq")).isNotNull(),
                () -> assertThat(this.chain.getRequest().getParameterMap().get("userIdEq")).isNotEmpty(),
                () -> assertThat(this.chain.getRequest().getParameterMap().get("userIdEq")[0]).isEqualTo("leejy"),
                () -> assertThat(this.chain.getRequest().getParameterMap().get("filter[user_id]")).isNull()
        );
    }

    @Test
    void filterTest() throws ServletException, IOException {
        OncePerRequestFilter filter = new ConventionWebMvcConfigResolver().snakeCaseConverterFilter(this.webProperties);
        this.request.addParameter("filter[user_id][like]", "leejy");

        filter.doFilter(this.request, this.response, this.chain);
        assertAll(
                () -> assertThat(this.chain.getRequest().getParameterMap()).isNotNull(),
                () -> assertThat(this.chain.getRequest().getParameterMap().get("userId")).isNull(),
                () -> assertThat(this.chain.getRequest().getParameterMap().get("user_id")).isNull(),
                () -> assertThat(this.chain.getRequest().getParameterMap().get("userIdLike")).isNotNull(),
                () -> assertThat(this.chain.getRequest().getParameterMap().get("userIdLike")).isNotEmpty(),
                () -> assertThat(this.chain.getRequest().getParameterMap().get("userIdLike")[0]).isEqualTo("leejy"),
                () -> assertThat(this.chain.getRequest().getParameterMap().get("filter[user_id][like]")).isNull()
        );
    }
}