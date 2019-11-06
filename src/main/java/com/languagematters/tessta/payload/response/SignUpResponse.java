package com.languagematters.tessta.payload.response;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class SignUpResponse {

    @NonNull
    private Boolean success;

    @NonNull
    private String message;

}