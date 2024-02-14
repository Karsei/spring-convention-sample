package kr.pe.karsei.convention.dto.rest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MandatoryErrorApiResponse implements ErrorApiResponse {
    private MandatoryError error;
}
