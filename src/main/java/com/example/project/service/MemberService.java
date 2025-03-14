package com.example.project.service;

import com.example.project.DAO.MemberDAO;
import com.example.project.dto.LoginRequestDTO;
import com.example.project.dto.LoginResponseDTO;
import com.example.project.dto.MemberRequestDTO;
import com.example.project.dto.MemberResponseDTO;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberDAO memberDAO;

    public void save(MemberRequestDTO memberRequestDTO) {
        memberDAO.save(memberRequestDTO);
    }

    public MemberResponseDTO findMember(MemberRequestDTO memberRequestDTO) {
        MemberResponseDTO memberResponseDTO = memberDAO.findMember(memberRequestDTO.getUsername());
        return memberResponseDTO;
    }

    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) throws BadRequestException {
        LoginResponseDTO loginResponseDTO = memberDAO.login(loginRequestDTO);
        return loginResponseDTO;
    }

}
