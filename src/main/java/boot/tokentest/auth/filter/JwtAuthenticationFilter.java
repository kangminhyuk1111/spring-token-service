package boot.tokentest.auth.filter;

import boot.tokentest.auth.provider.TokenProvider;
import boot.tokentest.auth.repository.JwtRepository;
import boot.tokentest.auth.service.JwtService;
import boot.tokentest.global.common.response.ErrorResponse;
import boot.tokentest.global.exception.ApplicationException;
import boot.tokentest.global.exception.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.net.http.HttpResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    public static final String AUTHORIZATION_KEY = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";

    private final TokenProvider tokenProvider;
    private final JwtRepository jwtRepository;
    private final WhiteListUrl whiteListUrl;
    private final JwtService jwtService;

    public JwtAuthenticationFilter(final TokenProvider tokenProvider, final JwtRepository jwtRepository, final WhiteListUrl whiteListUrl, final JwtService jwtService) {
        this.tokenProvider = tokenProvider;
        this.jwtRepository = jwtRepository;
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
            final String jti = tokenProvider.extractJtiFromToken(accessToken);

            validateJti(jti);
            validateTokenActive(accessToken);
            validateTokenEqualsFoundToken(jti, accessToken);

            filterChain.doFilter(request, response);
        } catch (ApplicationException e) {
            handleException(response, e);
        }
    }

    private void handleException(HttpServletResponse response, ApplicationException e) {
        try {
            ErrorCode errorCode = e.getErrorCode();
            ErrorResponse errorResponse = ErrorResponse.of(errorCode);

            response.setStatus(errorCode.getStatus().value());
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            String jsonResponse = new ObjectMapper().writeValueAsString(errorResponse);
            response.getWriter().write(jsonResponse);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    private void validateTokenEqualsFoundToken(final String jti, final String accessToken) {
        if (!jwtRepository.isAccessTokenPresent(jti, accessToken)) {
            throw new ApplicationException(ErrorCode.UNAUTHORIZED);
        }
    }

    private void validateTokenActive(final String accessToken) {
        if (!jwtService.isTokenActive(accessToken)) {
            throw new ApplicationException(ErrorCode.INVALID_ACCESS_TOKEN);
        }
    }

    private void validateJti(final String jti) {
        if (!jwtRepository.isPresentJti(jti)) {
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
