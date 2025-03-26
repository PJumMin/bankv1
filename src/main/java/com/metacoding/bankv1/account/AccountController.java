package com.metacoding.bankv1.account;

import com.metacoding.bankv1.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class AccountController {
    private final AccountService accountService;
    private final HttpSession session;

    // /account/1111?type=입금
    // 상세페이지
    @GetMapping("/account/{number}")
    public String detail(@PathVariable("number") int number, @RequestParam(required = false, defaultValue = "전체") String type) {
        //공통 부가로직
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) throw new RuntimeException("로그인 후 사용해주세요");
        System.out.println("number = " + number);
        System.out.println("type = " + type);
        accountService.계좌상세보기(number, type, sessionUser.getId());
        return "account/detail";
    }

    // 계좌이체
    @PostMapping("/account/transfer")
    public String transgfer(AccountRequest.TransferDTO transferDTO) {
        //공통 부가로직
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) throw new RuntimeException("로그인 후 사용해주세요");

        accountService.계좌이체(transferDTO, sessionUser.getId());

        //개발자들 컨벤션 수정이 필요할 때 TODO를 넣음
        return "redirect:/"; // TODO = detail로 가기
    }

    // 계좌이체 페이지
    @GetMapping("/account/transfer-form")
    public String transferForm() {
        //공통 부가로직
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) throw new RuntimeException("로그인 후 사용해주세요");

        return "account/transfer-form";
    }

    // 홈페이지
    @GetMapping("/")
    public String home() {
        return "home";
    }

    // 계좌생성 페이지
    @GetMapping("/account/save-form")
    public String saveForm() {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) throw new RuntimeException("로그인 후 사용해주세요");
        return "account/save-form";
    }

    // 계좌생성
    @PostMapping("/account/save")
    public String save(AccountRequest.SaveDTO saveDTO) {
        // 인증체크 (로그인했는지 체크)
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) throw new RuntimeException("로그인 후 사용해주세요");

        accountService.계좌생성(saveDTO, sessionUser.getId());
        return "redirect:/";
    }

    // 계좌목록페이지
    @GetMapping("/account")
    public String account(HttpServletRequest request) {
        // 인증체크 (로그인했는지 체크)
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) throw new RuntimeException("로그인 후 사용해주세요");

        List<Account> accountList = accountService.나의계좌목록(sessionUser.getId());
        request.setAttribute("models", accountList);
        System.out.println(sessionUser.getId());
        return "account/list";
    }
}