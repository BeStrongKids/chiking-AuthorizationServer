package com.bestrongkids.authorizationserver.dto;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

@Setter
@Getter
public class UserDto {
    private String name;
    private String email;
    private String password;
    private String newPassword;

    UserDto(){}
    public UserDto(String name, String email, String password) {
        checkArgument(isNotEmpty(name), "name must be provided");
        checkArgument(
                !name.isEmpty() && name.length() <= 10,
                "name length must be between 1 and 10 characters"
        );
        checkNotNull(email, "email must be provided");
        checkNotNull(password, "password must be provided");

        this.name= name;
        this.email= email;
        this.password= password;
        this.newPassword= newPassword;

    }

    public UserDto(String name, String email, String password,String newPassword) {
        this(name,email,password);
        checkNotNull(newPassword, "new password must be provided");
        checkArgument(
                !Objects.equals(password, newPassword),
                "new password must be different from the current password"
        );

        this.newPassword = newPassword;


    }

}
