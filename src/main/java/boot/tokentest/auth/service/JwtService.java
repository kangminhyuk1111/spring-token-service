package boot.tokentest.auth.service;

import boot.tokentest.auth.domain.AuthCredential;
import boot.tokentest.auth.provider.JtiProvider;
import boot.tokentest.auth.provider.TokenProvider;
import boot.tokentest.auth.repository.JwtRepository;
import boot.tokentest.global.exception.ApplicationException;
import boot.tokentest.global.exception.ErrorCode;
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

    public AuthCredential refreshToken(final String refreshToken) {

        // jwt에서 jti, email 추출
        final String jti = tokenProvider.extractJtiFromToken(refreshToken);
        final String email = tokenProvider.extractEmailFromToken(refreshToken);

        // jti 기반 credential 찾기
        final AuthCredential foundAuthCredential = jwtRepository.findByJti(jti);

        // 찾은 credential.refreshToken과 유저가 입력한 refreshToken의 유효성 검사, 통과 시, 새로운 accessToken생성
        validateRefreshToken(refreshToken, foundAuthCredential);

        // 새로운 credential 발급
        final AuthCredential refreshAuthCredential = generateAuthCredential(email);

        // 새로운 jwt 토큰 정보 저장
        jwtRepository.saveCredential(refreshAuthCredential);
        
        // 리프레시 된 credential을 유저에게 전송
        return refreshAuthCredential;
    }

    private void validateRefreshToken(final String refreshToken, final AuthCredential foundAuthCredential) {
        if(!foundAuthCredential.refreshToken().equals(refreshToken)) {
            throw new ApplicationException(ErrorCode.REFRESH_NOT_FOUND);
        }
    }

    private String generateAccessToken(final String email, final String jti) {
        return tokenProvider.createToken(email, jti);
    }

    private String generateRefreshToken(final String email, final String jti) {
        return tokenProvider.createToken(email, jti);
    }

    public boolean isTokenActive(final String token) {
        return tokenProvider.extractExpirationTimeFromToken(token).after(new Date());
    }
}
