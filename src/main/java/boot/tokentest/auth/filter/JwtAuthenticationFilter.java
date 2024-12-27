package boot.tokentest.auth.filter;

import boot.tokentest.auth.service.JwtService;
import boot.tokentest.global.common.response.ErrorResponse;
import boot.tokentest.global.exception.ApplicationException;
import boot.tokentest.global.exception.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
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

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    public static final String AUTHORIZATION_KEY = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";
    public static final String CONTENT_TYPE = "application/json";
    public static final String CHARACTER_ENCODING = "UTF-8";

    private final WhiteListUrl whiteListUrl;
    private final JwtService jwtService;
    private final ObjectMapper objectMapper;

    public JwtAuthenticationFilter(final WhiteListUrl whiteListUrl, final JwtService jwtService, final ObjectMapper objectMapper) {
        this.whiteListUrl = whiteListUrl;
        this.jwtService = jwtService;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain) {
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
            handleException(response, new ApplicationException(ErrorCode.UNAUTHORIZED));
        }
    }

    private void handleException(HttpServletResponse response, ApplicationException e) {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(CONTENT_TYPE);
        response.setCharacterEncoding(CHARACTER_ENCODING);

        ErrorResponse errorResponse = new ErrorResponse(e.getErrorCode().getMessage(), e.getErrorCode().getStatus());

        try {
            String jsonResponse = objectMapper.writeValueAsString(errorResponse);
            response.getWriter().write(jsonResponse);
        } catch (IOException ioException) {
            logger.error("ioException: ", ioException);
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
