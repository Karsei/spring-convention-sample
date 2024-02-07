package kr.pe.karsei.convention.config;

import java.util.Map;

public interface CompatibleServletRequest {
    Map<String, String[]> getParameterMap();
    String[] getParameterValues(String parameterName);
}
