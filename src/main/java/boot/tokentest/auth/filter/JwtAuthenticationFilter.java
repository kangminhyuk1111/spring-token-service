package boot.tokentest.auth.filter;

import boot.tokentest.auth.domain.AuthCredential;
import boot.tokentest.auth.repository.JwtRepository;
import boot.tokentest.auth.service.JwtService;
import boot.tokentest.global.exception.ApplicationException;
import boot.tokentest.global.exception.ErrorCode;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    public static final String AUTHORIZATION_KEY = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";
    public static final String JTI = "jti";

    private final JwtRepository jwtRepository;
    private final WhiteListUrl whiteListUrl;
    private final JwtService jwtService;

    public JwtAuthenticationFilter(final JwtRepository jwtRepository, final WhiteListUrl whiteListUrl, final JwtService jwtService) {
        this.jwtRepository = jwtRepository;
        this.whiteListUrl = whiteListUrl;
        this.jwtService = jwtService;
    }

    // 토큰이 유효하면 넘기기
    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain) throws ServletException, IOException {
        final String authToken = request.getHeader(AUTHORIZATION_KEY);
        final String jti = request.getHeader(JTI);
        final String uri = request.getRequestURI();
        final HttpMethod method = HttpMethod.valueOf(request.getMethod());

        // 1. 화이트 리스트 검증
        if (whiteListUrl.isAvailableUri(uri, method)) {
            filterChain.doFilter(request, response);
            return;
        }

        // 2. 토큰이 적절한지 검증
        if (authToken == null || !authToken.startsWith(BEARER_PREFIX)) {
            throw new ApplicationException(ErrorCode.TOKEN_NOT_FOUND);
        }

        // 3. 토큰앞에 Bearer 제거
        final String accessToken = authToken.substring(7); // Bearer ~~

        // 4. jti가 유효한지 검사
        if (!jwtRepository.isPresentJti(jti)) {
            throw new ApplicationException(ErrorCode.JTI_NOT_FOUND);
        }

        // 5. 토큰이 만료되었는지 확인. 만료시, refreshToken을 통한 토큰 재발급 구현
        if (!jwtService.isTokenActive(accessToken)) {
            // TODO refresh 로직 추가
            throw new ApplicationException(ErrorCode.UNAUTHORIZED);
        };

        // 6. 토큰이 jti로 찾은 토큰과 맞는지 검증
        if (!jwtRepository.isAccessTokenPresent(jti, accessToken)) {
            throw new ApplicationException(ErrorCode.UNAUTHORIZED);
        }

        filterChain.doFilter(request, response);
    }
}
