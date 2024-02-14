package kr.pe.karsei.conventiondemo.controller;

import kr.pe.karsei.convention.dto.rest.CollectApiResponse;
import kr.pe.karsei.convention.dto.rest.EntryApiResponse;
import kr.pe.karsei.convention.dto.rest.LimitedPageSize;
import kr.pe.karsei.convention.dto.rest.PageableApiResponse;
import kr.pe.karsei.convention.dto.rest.swagger.ConventionFilter;
import kr.pe.karsei.conventiondemo.dto.ErrorDto;
import kr.pe.karsei.conventiondemo.dto.TestDto;
import kr.pe.karsei.conventiondemo.exception.NotFoundException;
import kr.pe.karsei.conventiondemo.exception.ValidationException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Test", description = "컨벤션 테스트")
@RestController
@RequestMapping
public class TestController {
    private static final TestDto TEST_OBJ = new TestDto(
            "yay",
            1L,
            new TestDto.SomeObject("Hello", 2L),
            List.of(new TestDto.SomeObject("Hello", 2L))
    );

    @Operation(summary = "단일 조회 응답 API", description = """
            단일 조회 예시입니다.
            
            `data` 에 데이터를 담아서 나타냅니다.
            """)
    @GetMapping("entry")
    public EntryApiResponse<TestDto> entryTest() {
        return EntryApiResponse.of(TEST_OBJ);
    }

    @Operation(summary = "복수 조회 응답 API", description = """
            복수 조회 예시입니다.
            
            `data` 는 복수 개의 데이터도 담을 수 있습니다.
            """)
    @GetMapping("collect")
    public CollectApiResponse<TestDto> collectTest() {
        List<TestDto> obj = List.of(TEST_OBJ);
        return CollectApiResponse.of(obj);
    }

    @Operation(summary = "페이지 조회 응답 API", description = """
            페이지 조회 예시입니다.
            
            `meta.page` 에 pagination 과 관련된 항목들이 출력됩니다.
            """)
    @GetMapping("pagination")
    public PageableApiResponse<TestDto> paginationTest(@ParameterObject TestDto.TestRequest request,
                                                       @ParameterObject @ConventionFilter Pageable pageable) {
        List<TestDto> obj = List.of(TEST_OBJ);
        Page<TestDto> page = new PageImpl<>(obj, pageable, obj.size());
        return PageableApiResponse.of(page);
    }

    @Operation(summary = "페이지 조회 응답 API (기존 API 호환)", description = """
            페이지 조회 예시입니다.
            
            기존 API 에서 영향을 받는지, 호환성을 확인하기 위한 용도입니다.
            """)
    @GetMapping("pagination-legacy")
    public PageableApiResponse<TestDto> paginationLegacyTest(@ParameterObject TestDto.TestLegacyRequest request,
                                                             @ParameterObject Pageable pageable) {
        List<TestDto> obj = List.of(TEST_OBJ);
        Page<TestDto> page = new PageImpl<>(obj, pageable, obj.size());
        return PageableApiResponse.of(page);
    }

    @Operation(summary = "페이지 조회 최대 페이지 사이즈 응답 API", description = """
            최대 페이지 사이즈를 지정했을 때 페이지 조회 예시입니다.
            
            `size` 를 아무리 크게 하더라도 현재 endpoint 에서 지정된 1000으로 고정됩니다.
            """)
    @GetMapping("pagination-max-size")
    public PageableApiResponse<TestDto> paginationMaxSizeTest(
            @ParameterObject @PageableDefault(size = 15) @ConventionFilter @LimitedPageSize(maxSize = 1000) Pageable pageable) {
        List<TestDto> obj = List.of(TEST_OBJ);
        Page<TestDto> page = new PageImpl<>(obj, pageable, obj.size());
        return PageableApiResponse.of(page);
    }

    @Operation(summary = "페이지 조회 최대 페이지 사이즈 응답 API (기존 API 호환)", description = """
            최대 페이지 사이즈를 지정했을 때 페이지 조회 예시입니다.
            
            `size` 를 아무리 크게 하더라도 현재 endpoint 에서 지정된 1000으로 고정됩니다.
            
            기존 API 에서 영향을 받는지, 호환성을 확인하기 위한 용도입니다.
            """)
    @GetMapping("pagination-max-size-legacy")
    public PageableApiResponse<TestDto> paginationMaxSizeLegacyTest(
            @ParameterObject @PageableDefault(size = 15) @LimitedPageSize(maxSize = 1000) Pageable pageable) {
        List<TestDto> obj = List.of(TEST_OBJ);
        Page<TestDto> page = new PageImpl<>(obj, pageable, obj.size());
        return PageableApiResponse.of(page);
    }

    @Operation(summary = "오류 응답 API", description = """
            오류 응답 예시입니다.
            
            간단하게 오류 코드와 메시지 만이 출력됩니다.
            """)
    @GetMapping("error")
    public EntryApiResponse<String> errorTest() {
        if (true)
            throw new NotFoundException("테스트 오류입니다.");
        return EntryApiResponse.of("good");
    }

    @Operation(summary = "오류 세부 응답 API", description = """
            오류 세부 응답 예시입니다.
            
            오류 코드와 메시지, 그리고 세부 오류 정보가 출력됩니다.
            """)
    @GetMapping("error-list")
    public EntryApiResponse<String> errorListTest() {
        if (true)
            throw new ValidationException("테스트 오류입니다.", List.of(new ErrorDto("user", List.of("cat", "lion"))));
        return EntryApiResponse.of("good");
    }
}

