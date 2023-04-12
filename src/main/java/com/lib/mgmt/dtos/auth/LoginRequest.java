package com.lib.mgmt.dtos.auth;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class LoginRequest {

    @NotEmpty
    @Size(min = 4,max = 20)
    @Pattern(regexp = "^[a-zA-Z ]+$",message = "Special characters and Numbers not allowed")
    private String username;
    @NotEmpty
    @Size(min = 6,max = 20)
    @Pattern(regexp = "^[a-zA-Z0-9 ]+$",message = "Special characters not allowed")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password.trim();
    }
}
