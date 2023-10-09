package com.bestrongkids.authorizationserver.result.user;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class RegisterUserResult {

    private String username;
    private String description;

    protected RegisterUserResult(){}

    public RegisterUserResult(String username){
        this.username = username;
    }

    public RegisterUserResult(String username, String description){
        this.username = username;
        this.description = description;
    }
}
