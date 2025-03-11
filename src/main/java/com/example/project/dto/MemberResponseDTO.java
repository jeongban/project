package com.example.project.dto;

import com.example.project.Enum.Role;
import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
public class MemberResponseDTO {

    private Long Id;
    private String name;
    private String username;
    private String password;
    private Role role;

}
