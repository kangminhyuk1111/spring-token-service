package boot.tokentest.member.service;

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

    public Member signup(final MemberSignupRequestDto memberSignupRequestDto) {
        final String email = memberSignupRequestDto.getEmail();

        if(isMemberAlreadyExist(email)) {
            throw new IllegalArgumentException("Email already in use");
        }

        return memberRepository.save(memberSignupRequestDto.toEntity(passwordEncoder));
    }

    public void delete(final Member member) {
        memberRepository.delete(member);
    }

    public Member findByEmail(final String email) {
        return memberRepository.findByEmail(email);
    }

    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    private boolean isMemberAlreadyExist(final String email) {
        final Member member = memberRepository.findByEmail(email);

        return member != null;
    }
}