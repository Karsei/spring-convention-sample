package kr.pe.karsei.convention.dto.rest;

public interface ApiResponse<T> {
    /**
     * 데이터를 나타냅니다.
     *
     * <p>모든 응답은 data 라는 속성에 담아서 보냅니다. 단일 조회와 리스트 조회 모두 속성 명은 data로 동일하며,
     * 단일 또는 리스트로 나타낼 수 있습니다.</p>
     *
     * <pre>
     * // 단일 조회
     * {
     *   "data": {
     *     "id": 1,
     *     "name": "leejy"
     *   }
     * }
     * </pre>
     * <pre>
     * // 리스트 조회
     * {
     *   "data": [
     *     {
     *       "id": 1,
     *       "name": "leejy"
     *     },
     *     {
     *       "id": 2,
     *       "name": "some"
     *     }
     *   ]
     * }
     * </pre>
     *
     * <p>요청 리소스 외에 다른 리소스를 함께 응답해야 하는 경우는 data 외 속성명을 사용해 응답할 수 있습니다.</p>
     *
     * <pre>
     * {
     *   "data": {
     *     "id": 1,
     *     "name": "leejy",
     *     "companies": [2]
     *   },
     *   "company": {
     *     "id": 2,
     *     "name": "hong",
     *     "companies": [1, 5]
     *   }
     * }
     * </pre>
     *
     * @return 결과 데이터
     */
    T getData();
}
