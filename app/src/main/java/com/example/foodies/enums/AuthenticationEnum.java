package com.example.foodies.enums;

public enum AuthenticationEnum {

    UNAUTHORIZED("unauthorized"),
    AUTHORIZED("authorized");

    private String value;

    AuthenticationEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

}
