package boot.tokentest.auth.service;

import boot.tokentest.auth.domain.AuthCredential;
import boot.tokentest.auth.provider.JtiProvider;
import boot.tokentest.auth.provider.TokenProvider;
import boot.tokentest.auth.repository.JwtRepository;
import boot.tokentest.member.domain.Member;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {

    private final TokenProvider tokenProvider;
    private final JwtRepository jwtRepository;
    private final JtiProvider jtiProvider;

    public JwtService(final TokenProvider tokenProvider, final JwtRepository jwtRepository, final JtiProvider jtiProvider) {
        this.tokenProvider = tokenProvider;
        this.jwtRepository = jwtRepository;
        this.jtiProvider = jtiProvider;
    }

    public AuthCredential generateAuthCredential(final String email) {
        final String jti = jtiProvider.generateJti();

        final String accessToken = generateAccessToken(email, jti);
        final String refreshToken = generateRefreshToken(email, jti);

        final AuthCredential authCredential = new AuthCredential(jti, accessToken, refreshToken);

        jwtRepository.saveCredential(authCredential);

        return authCredential;
    }

    public Claims extractClaimsFromToken(final String token) {
        return tokenProvider.extractClaimsFromToken(token);
    }

    private String generateAccessToken(final String email, final String jti) {
        return tokenProvider.createToken(email, jti);
    }

    private String generateRefreshToken(final String email, final String jti) {
        return tokenProvider.createToken(email, jti);
    }

    public boolean isTokenActive(final String token) {
        return !tokenProvider.extractExpirationTimeFromClaims(token).after(new Date());
    }
}

// 로그인 -> return authcredential -> 헤더에 jti랑 accesstoken 물고오기 -> jti로 찾은 authcredential 내부에 accesstoken 검증
// accesstoken 유효하면 인가시킴 -> accesstoken이 유효하지 않으면 refreshtoken 체크 -> jti로 찾은 authcredential에 refresh 토큰과 사용자 토큰 일치 확인
// 일치한다면 새로운 authcredential 발급 -> 다시 요청할 때, 클라이언트가 새로운 accesstoken으로 요청 보내길 기대
