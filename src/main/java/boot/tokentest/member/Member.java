package boot.tokentest.member;

public class Member {

    private final String id;
    private final String pw;

    public Member(final String id, final String pw) {
        this.id = id;
        this.pw = pw;
    }

    public String getId() {
        return id;
    }
}
