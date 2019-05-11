package com.languagematters.tessta.web.payload.response;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ApiResponse {

    @NonNull
    private Boolean success;

    @NonNull
    private String message;

}