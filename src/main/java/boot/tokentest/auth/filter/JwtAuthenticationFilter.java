package boot.tokentest.auth.filter;

import boot.tokentest.auth.repository.JwtRepository;
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

    private final JwtRepository jwtRepository;
    private final WhiteListUrl whiteListUrl;

    public JwtAuthenticationFilter(final JwtRepository jwtRepository, final WhiteListUrl whiteListUrl) {
        this.jwtRepository = jwtRepository;
        this.whiteListUrl = whiteListUrl;
    }

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain) throws ServletException, IOException {
        final String authToken = request.getHeader(AUTHORIZATION_KEY);
        final String uri = request.getRequestURI();
        final HttpMethod method = HttpMethod.valueOf(request.getMethod());

        if (whiteListUrl.isAvailableUri(uri, method)) {
            filterChain.doFilter(request, response);
            return;
        }

        if (authToken == null || !authToken.startsWith(BEARER_PREFIX)) {
            throw new ApplicationException(ErrorCode.TOKEN_NOT_FOUND);
        }

        final String accessToken = authToken.substring(7); // Bearer ~~

        if (!jwtRepository.isAccessTokenPresent(accessToken)) {
            throw new ApplicationException(ErrorCode.UNAUTHORIZED);
        }

        filterChain.doFilter(request, response);
    }
}
