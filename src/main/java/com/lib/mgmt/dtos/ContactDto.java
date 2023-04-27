package com.lib.mgmt.dtos;

import lombok.*;

@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ContactDto {

    private int id;
    private String name;
    private String email;
    private String mobile;
    private String message;

}
