package boot.tokentest.member.repository;

import boot.tokentest.global.exception.ApplicationException;
import boot.tokentest.global.exception.ErrorCode;
import boot.tokentest.member.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class InMemoryMemberRepository implements MemberRepository{

    private final List<Member> members = new ArrayList<>();

    @Override
    public Member save(final Member member) {
        members.add(member);
        return member;
    }

    @Override
    public void delete(final Member member) {
        members.remove(member);
    }

    @Override
    public Member findByEmail(final String email) {
        return members.stream()
                .filter(member -> member.email().equals(email))
                .findFirst()
                .orElseThrow(() -> new ApplicationException(ErrorCode.NOT_FOUND_USER));
    }

    @Override
    public List<Member> findAll() {
        return members;
    }

    @Override
    public boolean existsByEmail(final String email) {
        return members.stream().anyMatch(member -> member.email().equals(email));
    }
}
