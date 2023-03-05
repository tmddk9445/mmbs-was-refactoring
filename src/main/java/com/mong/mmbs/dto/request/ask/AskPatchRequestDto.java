package com.mong.mmbs.dto.request.ask;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AskPatchRequestDto {
    @NotBlank
    private int askId;
    @NotBlank
    private String askWriter;
    @NotBlank
    private String askSort;
    @NotBlank
    private String askTitle;
    @NotBlank
    private String askContent;
}
