package com.bestrongkids.authorizationserver.result.user;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UpdateUserResult {

    private String username;
    private String description;

    protected UpdateUserResult(){}

    public UpdateUserResult(String username){
        this.username = username;
    }

    public UpdateUserResult(String username, String description){
        this.username = username;
        this.description = description;
    }
}
