package com.example.project.service;

import com.example.project.DAO.MemberDAO;
import com.example.project.dto.MemberRequestDTO;
import com.example.project.dto.MemberResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberDAO memberDAO;

    public void save(MemberRequestDTO memberRequestDTO) {
        memberDAO.save(memberRequestDTO);
    }

    public MemberResponseDTO findMember(MemberRequestDTO memberRequestDTO) {
        MemberResponseDTO memberResponseDTO = memberDAO.getMember(memberRequestDTO.getUsername());
        return memberResponseDTO;
    }

}
