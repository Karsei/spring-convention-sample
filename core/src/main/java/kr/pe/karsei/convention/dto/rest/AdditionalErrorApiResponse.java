package kr.pe.karsei.convention.dto.rest;

import java.util.List;

public interface AdditionalErrorApiResponse<T> extends ErrorApiResponse {
    /**
     * 제약 조건에 걸리는 오류에 대한 상세 정보들을 나타냅니다.
     * @return 오류 상세 정보들
     */
    List<T> getErrorList();
}
