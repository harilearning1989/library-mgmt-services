package com.lib.mgmt.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

public class Request {
    @NotEmpty
    @Size(min = 4,max = 20)
    @Pattern(regexp = "^[a-zA-Z0-9 ]+$",message = "Special characters not allowed")
    private String firstName;
    @NotEmpty
    @Size(min = 4,max = 20)
    @Pattern(regexp = "^[a-zA-Z0-9 ]+$",message = "Special characters not allowed")
    private String lastName;
    @NotEmpty
    @Size(min = 4,max = 20)
    @Pattern(regexp = "^[a-zA-Z ]+$",message = "Special characters and Numbers not allowed")
    private String username;
    @NotEmpty
    @Size(min = 6,max = 20)
    @Pattern(regexp = "^[a-zA-Z0-9 ]+$",message = "Special characters not allowed")
    private String password;
    //@Pattern(regexp = "^[a-zA-Z0-9.\\-\\/+=@_ ]*$")
    @Pattern(regexp = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$")
    @NotEmpty
    @Email
    @Size(max = 20)
    private String email;
    private String phone;
    private List<String> roles;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
