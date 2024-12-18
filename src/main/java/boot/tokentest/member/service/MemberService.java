package boot.tokentest.member.service;

import boot.tokentest.global.exception.ApplicationException;
import boot.tokentest.global.exception.ErrorCode;
import boot.tokentest.member.domain.Member;
import boot.tokentest.member.dto.MemberSignupRequestDto;
import boot.tokentest.member.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberService(final MemberRepository memberRepository, final PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String signup(final MemberSignupRequestDto memberSignupRequestDto) {
        final String email = memberSignupRequestDto.getEmail();

        if(memberRepository.existsByEmail(email)) {
            throw new ApplicationException(ErrorCode.MEMBER_ALREADY_EXIST);
        }

        final Member member = memberRepository.save(memberSignupRequestDto.toEntity(passwordEncoder));

        return member.email();
    }

    public Member findByEmail(final String email) {
        return memberRepository.findByEmail(email);
    }

    public List<Member> findAll() {
        return memberRepository.findAll();
    }
}
