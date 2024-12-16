package boot.tokentest.member.controller;

import boot.tokentest.member.domain.Member;
import boot.tokentest.member.dto.MemberSignupRequestDto;
import boot.tokentest.member.service.MemberService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    public MemberController(final MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping()
    public List<Member> findAll() {
        return memberService.findAll();
    }

    @PostMapping()
    public Member save(@RequestBody final MemberSignupRequestDto memberSignupRequestDto) {
        return memberService.signup(memberSignupRequestDto);
    }
}
