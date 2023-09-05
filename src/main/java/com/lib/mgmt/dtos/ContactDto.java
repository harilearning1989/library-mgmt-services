package com.lib.mgmt.dtos;

import lombok.*;

@Builder
@Data
public class ContactDto {

    private int id;
    private String name;
    private String email;
    private String mobile;
    private String message;

}
