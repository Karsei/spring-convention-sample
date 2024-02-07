package kr.pe.karsei.convention.dto.rest;

public enum FilterOperator {
    /**
     * 주어진 대상과 동일합니다. (EQUAL)
     * <p>ex) field = 'test'</p>
     */
    EQ,

    /**
     * 주어진 대상과 동일하지 않습니다. (NOT EQUAL)
     * <p>ex) field != 'test'</p>
     */
    NEQ,

    /**
     * 주어진 대상들이 포함됩니다. (IN)
     * <p>ex) field in ('test1', 'test2', 'test3')</p>
     */
    IN,

    /**
     * 주어진 대상들이 포함되지 않습니다. (NOT IN)
     * <p>ex) field not in ('test1', 'test2', 'test3')</p>
     */
    NIN,

    /**
     * 주어진 대상보다 작습니다. (LESS THAN)
     * <p>ex) field < 3</p>
     */
    LT,

    /**
     * 주어진 대상보다 작거나 같습니다. (LESS THAN OR EQUAL)
     * <p>ex) field <= 3</p>
     */
    LTE,

    /**
     * 주어진 대상보다 큽니다. (GREATER THAN)
     * <p>ex) field > 3</p>
     */
    GT,

    /**
     * 주어진 대상보다 크거나 같습니다. (GREATER THAN EQUAL)
     * <p>ex) field >= 3</p>
     */
    GTE,

    /**
     * 주어진 대상들 사이에 존재합니다. (IN RANGE)
     * <p>ex) field between 1 and 3</p>
     */
    BETWEEN,

    /**
     * 주어진 대상들 사이에 존재하지 않습니다. (NOT IN RANGE)
     * <p>ex) field not between 1 and 3</p>
     */
    NBETWEEN,

    /**
     * 주어진 대상이 문자열에 포함됩니다. (LIKE)
     * <p>ex) field LIKE %test%</p>
     */
    LIKE,

    /**
     * 주어진 대상이 문자열에 포함되지 않습니다. (NOT LIKE)
     * <p>ex) field NOT LIKE %test%</p>
     */
    NLIKE,
}