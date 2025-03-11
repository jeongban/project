package com.example.project.controller;

import com.example.project.dto.MemberRequestDTO;
import com.example.project.dto.MemberResponseDTO;
import com.example.project.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/login")
    public String login() {
        return "/login";
    }

    @GetMapping("/addMember")
    public String addMember(MemberRequestDTO memberRequestDTO, Model model) {
        model.addAttribute("memberFormDTO", memberRequestDTO);
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
}
