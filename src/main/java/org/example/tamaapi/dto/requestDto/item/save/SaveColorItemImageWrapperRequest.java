package org.example.tamaapi.dto.requestDto.item.save;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class SaveColorItemImageWrapperRequest {

    @Valid
    @NotEmpty
    private List<SaveColorItemImageRequest> requests;
}
