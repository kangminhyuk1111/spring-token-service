package boot.tokentest.auth.filter;

import boot.tokentest.auth.service.JwtService;
import boot.tokentest.global.common.response.ErrorResponse;
import boot.tokentest.global.exception.ApplicationException;
import boot.tokentest.global.exception.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final static Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    public static final String AUTHORIZATION_KEY = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";

    private final WhiteListUrl whiteListUrl;
    private final JwtService jwtService;

    public JwtAuthenticationFilter(final WhiteListUrl whiteListUrl, final JwtService jwtService) {
        this.whiteListUrl = whiteListUrl;
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain) throws ServletException, IOException {
        try {
            final String authToken = request.getHeader(AUTHORIZATION_KEY);
            final String uri = request.getRequestURI();
            final HttpMethod method = HttpMethod.valueOf(request.getMethod());

            if (whiteListUrl.isAvailableUri(uri, method)) {
                filterChain.doFilter(request, response);
                return;
            }

            validateAuthToken(authToken);

            final String accessToken = authToken.substring(7);

            validateJti(accessToken);
            validateTokenActive(accessToken);
            validateTokenEqualsFoundToken(accessToken);

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
        }
    }

    private void validateTokenEqualsFoundToken(final String accessToken) {
        if (!jwtService.isAccessTokenPresent(accessToken)) {
            throw new ApplicationException(ErrorCode.UNAUTHORIZED);
        }
    }

    private void validateTokenActive(final String accessToken) {
        if (!jwtService.isTokenActive(accessToken)) {
            throw new ApplicationException(ErrorCode.INVALID_ACCESS_TOKEN);
        }
    }

    private void validateJti(final String token) {
        if (!jwtService.isPresentJti(token)) {
            throw new ApplicationException(ErrorCode.JTI_NOT_FOUND);
        }
    }

    private void validateAuthToken(final String authToken) {
        if (authToken == null || !authToken.startsWith(BEARER_PREFIX)) {
            throw new ApplicationException(ErrorCode.TOKEN_NOT_FOUND);
        }
    }
}

// 1. 화이트 리스트 검증
// 2. 토큰이 적절한지 검증
// 3. 토큰앞에 Bearer 제거
// 4. jti가 유효한지 검사
// 5. 토큰이 만료되었는지 확인. 만료시, refreshToken을 통한 토큰 재발급 구현
// 6. 토큰이 jti로 찾은 토큰과 맞는지 검증
