package com.example.project.dto;

import com.example.project.Enum.Role;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberRequestDTO {

    // 생성 인덱스
    private Long insertId;

    // 아이디
    private String username;

    // 이름
    private String name;

    // 비밀번호
    private String password;

    // 권한
    private Role role;
}
