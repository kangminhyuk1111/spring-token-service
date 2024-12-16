package boot.tokentest.member.repository;

import boot.tokentest.member.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MemberRepositoryImpl implements MemberRepository{

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
        return members.stream().filter(member -> member.getEmail().equals(email)).findFirst().orElse(null);
    }

    @Override
    public List<Member> findAll() {
        return members;
    }
}
