package kr.pe.karsei.conventiondemo.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.pe.karsei.convention.config.ConventionWebMvcConfigResolver;
import kr.pe.karsei.convention.dto.rest.*;
import kr.pe.karsei.conventiondemo.dto.ErrorDto;
import kr.pe.karsei.conventiondemo.dto.TestDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest
@AutoConfigureMockMvc
@Import(ConventionWebMvcConfigResolver.class)
class TestControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testIfRetrievingEntryItemIsSuccessful() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/entry"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        EntryApiResponse<TestDto> result = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {});

        assertAll(
                () -> Assertions.assertThat(result).isNotNull(),
                () -> Assertions.assertThat(result.getData()).isNotNull(),
                () -> Assertions.assertThat(result.getData().getObjVal()).isNotNull(),
                () -> Assertions.assertThat(result.getData().getObjListVal()).isNotNull(),
                () -> Assertions.assertThat(result.getData().getLongVal()).isNotNull(),
                () -> Assertions.assertThat(result.getData().getStrVal()).isNotBlank()
        );
    }

    @Test
    void testIfRetrievingCollectionItemsIsSuccessful() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/collect"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        CollectApiResponse<TestDto> result = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {});

        assertAll(
                () -> Assertions.assertThat(result).isNotNull(),
                () -> Assertions.assertThat(result.getData()).isNotNull()
        );
        result.getData().forEach(data -> assertAll(
                () -> Assertions.assertThat(data).isNotNull(),
                () -> Assertions.assertThat(data.getObjVal()).isNotNull(),
                () -> Assertions.assertThat(data.getObjListVal()).isNotNull(),
                () -> Assertions.assertThat(data.getLongVal()).isNotNull(),
                () -> Assertions.assertThat(data.getStrVal()).isNotBlank()
        ));
    }

    @Test
    void testIfRetrievingPaginationItemsIsSuccessful() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/pagination")
                        .param("page[offset]", "2")
                        .param("page[limit]", "30")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        PageableApiResponse<TestDto> result = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {});

        assertAll(
                () -> Assertions.assertThat(result).isNotNull(),
                () -> Assertions.assertThat(result.getData()).isNotNull(),
                () -> Assertions.assertThat(result.getMeta()).isNotNull(),
                () -> Assertions.assertThat(result.getMeta().getPage()).isNotNull(),
                () -> Assertions.assertThat(result.getMeta().getPage().getTotal()).isEqualTo(2 * 30 + 1),
                () -> Assertions.assertThat(result.getMeta().getPage().getLimit()).isEqualTo(30),
                () -> Assertions.assertThat(result.getMeta().getPage().getOffset()).isEqualTo(2 * 30)
        );
        result.getData().forEach(data -> assertAll(
                () -> Assertions.assertThat(data).isNotNull(),
                () -> Assertions.assertThat(data.getObjVal()).isNotNull(),
                () -> Assertions.assertThat(data.getObjListVal()).isNotNull(),
                () -> Assertions.assertThat(data.getLongVal()).isNotNull(),
                () -> Assertions.assertThat(data.getStrVal()).isNotBlank()
        ));
    }

    @Test
    void testIfRetrievingPaginationItemsWithUnlimitIsSuccessful() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/pagination")
                        .param("page[offset]", "2")
                        .param("page[limit]", "30")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        PageableApiResponse<TestDto> result = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {});

        assertAll(
                () -> Assertions.assertThat(result).isNotNull(),
                () -> Assertions.assertThat(result.getData()).isNotNull(),
                () -> Assertions.assertThat(result.getMeta()).isNotNull(),
                () -> Assertions.assertThat(result.getMeta().getPage()).isNotNull(),
                () -> Assertions.assertThat(result.getMeta().getPage().getTotal()).isEqualTo(2 * 30 + 1),
                () -> Assertions.assertThat(result.getMeta().getPage().getLimit()).isEqualTo(30),
                () -> Assertions.assertThat(result.getMeta().getPage().getOffset()).isEqualTo(2 * 30)
        );
        result.getData().forEach(data -> assertAll(
                () -> Assertions.assertThat(data).isNotNull(),
                () -> Assertions.assertThat(data.getObjVal()).isNotNull(),
                () -> Assertions.assertThat(data.getObjListVal()).isNotNull(),
                () -> Assertions.assertThat(data.getLongVal()).isNotNull(),
                () -> Assertions.assertThat(data.getStrVal()).isNotBlank()
        ));
    }

    @Test
    void testIfRetrievingPaginationItemsWithLegacyIsSuccessful() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/pagination-legacy"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        PageableApiResponse<TestDto> result = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {});

        assertAll(
                () -> Assertions.assertThat(result).isNotNull(),
                () -> Assertions.assertThat(result.getData()).isNotNull(),
                () -> Assertions.assertThat(result.getMeta()).isNotNull(),
                () -> Assertions.assertThat(result.getMeta().getPage()).isNotNull(),
                () -> Assertions.assertThat(result.getMeta().getPage().getTotal()).isGreaterThanOrEqualTo(0),
                () -> Assertions.assertThat(result.getMeta().getPage().getLimit()).isGreaterThanOrEqualTo(0),
                () -> Assertions.assertThat(result.getMeta().getPage().getOffset()).isGreaterThanOrEqualTo(0)
        );
        result.getData().forEach(data -> assertAll(
                () -> Assertions.assertThat(data).isNotNull(),
                () -> Assertions.assertThat(data.getObjVal()).isNotNull(),
                () -> Assertions.assertThat(data.getObjListVal()).isNotNull(),
                () -> Assertions.assertThat(data.getLongVal()).isNotNull(),
                () -> Assertions.assertThat(data.getStrVal()).isNotBlank()
        ));
    }

    @Test
    void testIfRetrievingPaginationItemsWithMaxSizeIsSuccessful() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/pagination-max-size")
                        .param("page[offset]", "0")
                        .param("page[limit]", "5000")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        PageableApiResponse<TestDto> result = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {});

        assertAll(
                () -> Assertions.assertThat(result).isNotNull(),
                () -> Assertions.assertThat(result.getData()).isNotNull(),
                () -> Assertions.assertThat(result.getMeta()).isNotNull(),
                () -> Assertions.assertThat(result.getMeta().getPage()).isNotNull(),
                () -> Assertions.assertThat(result.getMeta().getPage().getTotal()).isEqualTo(1),
                () -> Assertions.assertThat(result.getMeta().getPage().getLimit()).isEqualTo(1000),
                () -> Assertions.assertThat(result.getMeta().getPage().getOffset()).isEqualTo(0)
        );
        result.getData().forEach(data -> assertAll(
                () -> Assertions.assertThat(data).isNotNull(),
                () -> Assertions.assertThat(data.getObjVal()).isNotNull(),
                () -> Assertions.assertThat(data.getObjListVal()).isNotNull(),
                () -> Assertions.assertThat(data.getLongVal()).isNotNull(),
                () -> Assertions.assertThat(data.getStrVal()).isNotBlank()
        ));
    }

    @Test
    void testIfRetrievingPaginationItemsWithMaxSizeLegacyIsSuccessful() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/pagination-max-size-legacy")
                        .param("page", "0")
                        .param("size", "5000")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        PageableApiResponse<TestDto> result = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {});

        assertAll(
                () -> Assertions.assertThat(result).isNotNull(),
                () -> Assertions.assertThat(result.getData()).isNotNull(),
                () -> Assertions.assertThat(result.getMeta()).isNotNull(),
                () -> Assertions.assertThat(result.getMeta().getPage()).isNotNull(),
                () -> Assertions.assertThat(result.getMeta().getPage().getTotal()).isEqualTo(1),
                () -> Assertions.assertThat(result.getMeta().getPage().getLimit()).isEqualTo(1000),
                () -> Assertions.assertThat(result.getMeta().getPage().getOffset()).isEqualTo(0)
        );
        result.getData().forEach(data -> assertAll(
                () -> Assertions.assertThat(data).isNotNull(),
                () -> Assertions.assertThat(data.getObjVal()).isNotNull(),
                () -> Assertions.assertThat(data.getObjListVal()).isNotNull(),
                () -> Assertions.assertThat(data.getLongVal()).isNotNull(),
                () -> Assertions.assertThat(data.getStrVal()).isNotBlank()
        ));
    }

    @Test
    void testIfRetrievingErrorResponseIsSuccessful() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/error"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andReturn();
        MandatoryErrorApiResponse result = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {});

        assertAll(
                () -> Assertions.assertThat(result).isNotNull(),
                () -> Assertions.assertThat(result.getError().getCode()).isNotNull(),
                () -> Assertions.assertThat(result.getError().getMessage()).isNotBlank()
        );
    }

    @Test
    void testIfRetrievingErrorResponseWithDetailListIsSuccessful() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/error-list"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andReturn();
        DetailErrorApiResponse<ErrorDto> result = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {});

        assertAll(
                () -> Assertions.assertThat(result).isNotNull(),
                () -> Assertions.assertThat(result.getError().getCode()).isNotNull(),
                () -> Assertions.assertThat(result.getError().getMessage()).isNotBlank(),
                () -> Assertions.assertThat(result.getError().getDetails()).isNotNull(),
                () -> Assertions.assertThat(result.getError().getDetails()).isNotEmpty(),
                () -> Assertions.assertThat(result.getError().getDetails().get(0).getName()).isNotEmpty(),
                () -> Assertions.assertThat(result.getError().getDetails().get(0).getArguments()).isNotEmpty()
        );
    }
}