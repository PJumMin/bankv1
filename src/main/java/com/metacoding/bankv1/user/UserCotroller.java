package com.metacoding.bankv1.user;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class UserCotroller {
    private final UserService userService;
    private final HttpSession session;

    // 로그아웃
    @GetMapping("/logout")
    public String logout() {
        session.invalidate();
        return "redirect:/";
    }

    // 로그인
    // 로그인만 예외로 post (조회시에도)
    @PostMapping("/login") //password를 숨기기 위해서 @Post를 사용.
    public String login(UserRequest.LoginDTO loginDTO) {
        User sessionUser = userService.로그인(loginDTO);
        session.setAttribute("sessionUser", sessionUser); //stateful
        return "redirect:/";
    }

    // 로그인 페이지
    @GetMapping("/login-form")
    public String loginForm() {
        return "user/login-form";
    }

    // 회원가입 페이지
    @GetMapping("/join-form")
    public String joinForm() {
        return "user/join-form";
    }

    // 회원가입
    @PostMapping("/join")
    public String join(UserRequest.JoinDTO joinDTO) {
        System.out.println(joinDTO);
        userService.회원가입(joinDTO);
        return "redirect:/login-form";
    }
}
