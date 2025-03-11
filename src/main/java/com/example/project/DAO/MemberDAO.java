package com.example.project.DAO;

import com.example.project.dto.MemberRequestDTO;
import com.example.project.dto.MemberResponseDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MemberDAO {

    public MemberResponseDTO getMember(@Param("username") String username);

    public void save(MemberRequestDTO member);
}
