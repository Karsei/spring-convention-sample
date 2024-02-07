package kr.pe.karsei.convention.dto.rest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CollectApiResponse<T> implements ApiResponse<Collection<T>> {
    private Collection<T> data;

    public static <T> CollectApiResponse<T> of(Collection<T> data) {
        return new CollectApiResponse<>(data);
    }
}
