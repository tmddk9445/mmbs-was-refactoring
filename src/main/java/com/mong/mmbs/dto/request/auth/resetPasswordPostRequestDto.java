package com.mong.mmbs.dto.request.auth;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class resetPasswordPostRequestDto {
	private String userId;
	@NotBlank
	private String userPassword;
	private String userPassword2;
}