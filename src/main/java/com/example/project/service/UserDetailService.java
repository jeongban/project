package com.example.project.service;

import com.example.project.DAO.MemberDAO;
import com.example.project.config.UserDetailImpl;
import com.example.project.dto.MemberResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {

    private final MemberDAO memberDAO;

    @Override
    public UserDetails loadUserByUsername(String username){
        MemberResponseDTO memberResponseDTO = memberDAO.getMember(username);
        if(memberResponseDTO == null){
            throw new UsernameNotFoundException(username);
        }
        return UserDetailImpl.build(memberResponseDTO);
    }

}
