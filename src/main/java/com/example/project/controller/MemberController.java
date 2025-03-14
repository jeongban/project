package com.example.project.controller;

import com.example.project.config.Admin;
import com.example.project.dto.LoginRequestDTO;
import com.example.project.dto.LoginResponseDTO;
import com.example.project.dto.MemberRequestDTO;
import com.example.project.dto.MemberResponseDTO;
import com.example.project.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/login")
    public String login() {
        return "/member/Login";
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO, HttpServletResponse httpResponse) throws BadRequestException {
        LoginResponseDTO loginResponseDTO = memberService.login(loginRequestDTO);
        httpResponse.setHeader("Authorization", "Bearer " + loginResponseDTO.getToken());
        return ResponseEntity.ok(loginResponseDTO);
    }

    @GetMapping("/addMember")
    public String addMember() {
        return "/member/AddMember";
    }

    @PostMapping("/addMember")
    public String memberForm(MemberRequestDTO memberRequestDTO){
        memberService.save(memberRequestDTO);
        return "redirect:/";
    }

    @GetMapping("/getMember")
    public String getMember(MemberRequestDTO memberRequestDTO, Model model) {
        MemberResponseDTO memberResponseDTO = memberService.findMember(memberRequestDTO);
        model.addAttribute("memberResponseDTO", memberResponseDTO);
        return "redirect:/";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/admin")
    public ResponseEntity<MemberResponseDTO> admin(@AuthenticationPrincipal User user) throws BadRequestException {
        System.out.println(user.getAuthorities());
        MemberRequestDTO memberRequestDTO = new MemberRequestDTO();
        memberRequestDTO.setUsername(user.getUsername());
        MemberResponseDTO response = memberService.findMember(memberRequestDTO);
        return ResponseEntity.ok(response);
    }
}
