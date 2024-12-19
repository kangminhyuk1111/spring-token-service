package boot.tokentest.member.controller;

import boot.tokentest.member.domain.Member;
import boot.tokentest.member.dto.MemberSignupRequestDto;
import boot.tokentest.member.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<Member>> findAll() {
        List<Member> members = memberService.findAll();
        return new ResponseEntity<>(members, HttpStatus.OK) ;
    }

    @PostMapping()
    public ResponseEntity<String> save(@RequestBody final MemberSignupRequestDto memberSignupRequestDto) {
        final String email = memberService.signup(memberSignupRequestDto);
        return new ResponseEntity<>(email, HttpStatus.OK);
    }
}
