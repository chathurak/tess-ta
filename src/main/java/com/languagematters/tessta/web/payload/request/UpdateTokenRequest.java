package com.languagematters.tessta.web.payload.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UpdateTokenRequest {

    @NotBlank
    private String accessToken;

    @NotBlank
    private String imageUrl;

}
