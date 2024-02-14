package kr.pe.karsei.convention.dto.rest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DetailErrorApiResponse<T> implements ErrorApiResponse {
    private DetailError<T> error;
}
