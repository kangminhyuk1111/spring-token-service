package boot.tokentest.member.repository;

import boot.tokentest.member.domain.Member;

import java.util.List;

public interface MemberRepository {

    Member save(Member member);

    void delete(Member member);

    Member findByEmail(String email);

    List<Member> findAll();
}
