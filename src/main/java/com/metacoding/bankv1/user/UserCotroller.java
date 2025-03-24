package com.metacoding.bankv1.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class UserCotroller {

    private final UserServeice userServeice;

    //회원가입 페이지
    @GetMapping("/join-form")
    public String joinForm() {
        return "user/join-form";
    }

    //회원가입
    @PostMapping("/join")
    public String join(UserRequest.JoinDTO joinDTO) {
        System.out.println(joinDTO);
        userServeice.회원가입(joinDTO);
        return "redirect:/login-form";
    }

    //로그인 페이지
    @GetMapping("/login-form")
    public String loginForm() {
        return "user/login-form";
    }
}
