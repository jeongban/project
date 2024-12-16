package com.example.project.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberFormDTO {

    // 아이디
    private String username;

    // 이름
    private String name;

    // 비밀번호
    private String password;
}
