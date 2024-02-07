package kr.pe.karsei.convention.dto.rest;

public interface AdditionalApiResponse<T> extends ApiResponse<T> {
    /**
     * 부가 정보를 나타냅니다.
     *
     * <p>응답에 대한 부가 정보를 응답해야 하는 경우 해당 인터페이스를 사용하여 함께 제공할 수 있습니다. 예를 들어, 리스트 조회 시 페이지에 대한 정보를
     * 제공해줄 수 있습니다.</p>
     *
     * <p>아래는 응답 시 나타나는 예시입니다.</p>
     * <pre>
     * {
     *   "data": [
     *     {
     *       "id": 1,
     *       "name": "charlie"
     *     },
     *     {
     *       "id": 2,
     *       "name": "kim"
     *     }
     *   ],
     *   "meta": {
     *     "page": {
     *       "total": 10,
     *       "count": 2,
     *       "limit": 2,
     *       "offset": 4
     *     }
     *   }
     * }
     * </pre>
     *
     * <p>자세한 사항은 <a href="https://projectlombok.org/features/constructor">Confluence 문서</a>를 참고하세요.</p>
     * @return 부가 정보
     */
    Meta getMeta();
}
