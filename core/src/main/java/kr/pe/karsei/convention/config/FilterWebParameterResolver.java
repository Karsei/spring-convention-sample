package kr.pe.karsei.convention.config;

import com.google.common.base.CaseFormat;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FilterWebParameterResolver {
    public static Map<String, String[]> conventionFilter(CompatibleServletRequest compatibleServletRequest,
                                                         SpringDataWebProperties webProperties) {
        final Map<String, String[]> parameters = new HashMap<>();
        final Map<Integer, String> sortParam = new TreeMap<>();

        for (String param : compatibleServletRequest.getParameterMap().keySet()) {
            // filter 대응 (기본 SNAKE_CASE)
            if (param.startsWith("filter[")) {
                final Pattern filterPattern = Pattern.compile("filter\\[([^\\[\\]]+)\\](\\[([^\\[\\]]+)\\])?");
                Matcher matcher = filterPattern.matcher(param);
                if (matcher.matches()) {
                    final String paramName = matcher.group(1), // ex) filter[user_id][like] -> user_id
                            paramOperator = StringUtils.hasText(matcher.group(3)) ? matcher.group(3) : "eq",  // ex) filter[user_id][like] -> like
                            capitalizedOperator = paramOperator.substring(0, 1).toUpperCase() + paramOperator.substring(1);

                    final String camelCaseName = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, paramName);
                    parameters.put(String.format("%s%s", camelCaseName, capitalizedOperator), compatibleServletRequest.getParameterValues(param));
                }
            }
            // page 대응
            else if (param.startsWith("page[")) {
                final Pattern filterPattern = Pattern.compile("page\\[([^\\[\\]]+)\\](\\[([^\\[\\]]+)\\])?");
                Matcher matcher = filterPattern.matcher(param);

                if (matcher.matches()) {
                    String name;

                    final String paramName = matcher.group(1);
                    switch (paramName) {
                        case "limit" -> {
                            name = webProperties.getPageable().getSizeParameter(); // "size"
                            parameters.put(name, compatibleServletRequest.getParameterValues(param));
                        }
                        case "offset" -> {
                            name = webProperties.getPageable().getPageParameter(); // "page"
                            parameters.put(name, compatibleServletRequest.getParameterValues(param));
                        }
                    }
                }
            }
            // sort 대응
            else if (param.startsWith("sort[")) {
                // 값이 들어있지 않다면 건너뜀
                if (!StringUtils.hasText(compatibleServletRequest.getParameterValues(param)[0]) || !List.of("asc", "desc").contains(compatibleServletRequest.getParameterValues(param)[0].toLowerCase()))
                    continue;

                final String value = compatibleServletRequest.getParameterValues(param)[0].toLowerCase();
                final Pattern filterPattern = Pattern.compile("sort\\[([^\\[\\]]+)\\](\\[([^\\[\\]]+)\\])?");
                Matcher matcher = filterPattern.matcher(param);

                if (matcher.matches()) {
                    String paramName = matcher.group(3) != null ? matcher.group(3) : matcher.group(1);

                    int paramIdx;
                    try {
                        // 순서를 지정한 경우는 존중해줌
                        paramIdx = matcher.group(3) != null ? Integer.parseInt(matcher.group(1)) : 0;
                        while (sortParam.containsKey(paramIdx)) paramIdx++;
                    } catch (NumberFormatException e) {
                        continue;
                    }

                    sortParam.put(paramIdx, String.format("%s,%s", paramName, value));
                }
            }
            // 기존 파라미터 대응
            else {
                parameters.put(param, compatibleServletRequest.getParameterValues(param));
            }
        }

        // sort[ 형식으로 들어온 파라미터가 있으면 sort 대체
        if (!sortParam.isEmpty()) {
            parameters.put(webProperties.getSort().getSortParameter(), sortParam.keySet().stream()
                    .map(sortParam::get)
                    .toArray(String[]::new)
            );
        }

        return parameters;
    }
}
