package com.company.schedule.management.system.dto;

import lombok.*;

@Data
public class UserDTO {

    private Long id;
    private String email;
    private String password;
    private String role;
}
