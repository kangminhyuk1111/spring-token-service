package boot.tokentest.member.service;

import boot.tokentest.global.exception.ApplicationException;
import boot.tokentest.member.domain.Member;
import boot.tokentest.member.dto.MemberSignupRequestDto;
import boot.tokentest.member.repository.InMemoryMemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MemberServiceTest {

    private final MemberService memberService = new MemberService(new InMemoryMemberRepository(), new BCryptPasswordEncoder());

    @Test
    void 회원_가입_테스트() {
        // given
        final MemberSignupRequestDto memberSignupRequestDto = new MemberSignupRequestDto("test1234", "test1234@gmail.com", "testpassword1");

        // when
        final Member member = memberService.signup(memberSignupRequestDto);

        // then
        assertThat(member).isNotNull();
    }

    @Test
    void 회원_가입시_이메일_중복으로_ApplicationException_발생() {
        // given
        final MemberSignupRequestDto memberSignupRequestDto = new MemberSignupRequestDto("test1234", "test1234@gmail.com", "testpassword1");
        final MemberSignupRequestDto memberSignupRequestDtoExist = new MemberSignupRequestDto("test1234", "test1234@gmail.com", "testpassword1");

        // when
        memberService.signup(memberSignupRequestDto);

        // then
        assertThatThrownBy(() -> memberService.signup(memberSignupRequestDtoExist)).isInstanceOf(ApplicationException.class);
    }

    @Test
    void 이메일을_키로_멤버검색() {
        // given
        final MemberSignupRequestDto memberSignupRequestDto = new MemberSignupRequestDto("test1234", "test1234@gmail.com", "testpassword1");
        final Member member = memberService.signup(memberSignupRequestDto);

        // when
        final Member findByEmail = memberService.findByEmail(member.email());

        // then
        assertThat(findByEmail).isEqualTo(member);
    }

    @Test
    void 이메일을_키로_멤버검색시_존재하지_않는_이메일이라면_ApplicationException_발생() {
        // given
        final MemberSignupRequestDto memberSignupRequestDto = new MemberSignupRequestDto("test1234", "test1234@gmail.com", "testpassword1");
        memberService.signup(memberSignupRequestDto);

        // when & then
        assertThatThrownBy(() -> memberService.findByEmail("notfoundemail@gmail.com")).isInstanceOf(ApplicationException.class);
    }

    @Test
    void 모든_멤버_검색() {
        // given
        final MemberSignupRequestDto memberSignupRequestDto1 = new MemberSignupRequestDto("test1234", "test1234@gmail.com", "testpassword1");
        final MemberSignupRequestDto memberSignupRequestDto2 = new MemberSignupRequestDto("test123456", "test123456@gmail.com", "testpassword12");

        // when
        memberService.signup(memberSignupRequestDto1);
        memberService.signup(memberSignupRequestDto2);

        // then
        assertThat(memberService.findAll().size()).isEqualTo(2);
    }
}