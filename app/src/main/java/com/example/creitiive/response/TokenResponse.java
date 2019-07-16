package com.example.creitiive.response;

import com.example.creitiive.model.Token;

public class TokenResponse {

    private Token token;
    private int responseCode;
    private Throwable throwable;


    public TokenResponse(Token token, int responseCode, Throwable throwable) {
        this.token = token;
        this.responseCode = responseCode;
        this.throwable = throwable;
    }

    public Token getToken() {
        return token;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public Throwable getThrowable() {
        return throwable;
    }
}
