package com.example.project.DAO;

import com.example.project.JWT.JWTUtil;
import com.example.project.cmmn.AbstractMapper;
import com.example.project.dto.LoginRequestDTO;
import com.example.project.dto.LoginResponseDTO;
import com.example.project.dto.MemberRequestDTO;
import com.example.project.dto.MemberResponseDTO;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.apache.coyote.ErrorState;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@RequiredArgsConstructor
public class MemberDAO extends AbstractMapper {

    private final JWTUtil jwtUtil;

    @Transactional
    public MemberResponseDTO findMember(String username) {
        MemberResponseDTO memberResponseDTO = selectOne("MemberSQL.searchMember", username);
        return memberResponseDTO;
    }

    @Transactional
    public void save(MemberRequestDTO memberRequestDTO) {
        insert("MemberSQL.save", memberRequestDTO);
    }

    @Transactional
    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) throws BadRequestException {
        LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
        MemberResponseDTO memberResponseDTO = selectOne("MemberSQL.searchMember", loginRequestDTO.getUsername());

        if (memberResponseDTO == null) {
            throw new BadRequestException("아이디가 존재하지 않습니다.");
        }
        int passwordCheck = selectOne("MemberSQL.passwordCheck", loginRequestDTO);
        if (passwordCheck == 0) {
            throw new BadRequestException("비밀번호가 일치하지 않습니다.");
        }
        loginResponseDTO.setUsername(memberResponseDTO.getUsername());
        loginResponseDTO.setToken(jwtUtil.createToken(memberResponseDTO.getUsername()));
        return loginResponseDTO;
    }

}
