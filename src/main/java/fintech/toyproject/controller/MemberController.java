package fintech.toyproject.controller;

import fintech.toyproject.dto.MemberDto;
import fintech.toyproject.entitiy.Member;
import fintech.toyproject.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/user") //회원가입
    public String signUp(MemberDto memberDto){
        memberService.save(memberDto);
        return "redirect:/login";
    }

    @GetMapping(value = "logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response){
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        return "redirect:/login";
    }

    @GetMapping("/main")
    public String main(@AuthenticationPrincipal Member member, Model model){
        String name = member.getName();
        Integer balance = member.getBalance();
        model.addAttribute("name", name);
        model.addAttribute("balance", balance);

        return "main";

    }

}
