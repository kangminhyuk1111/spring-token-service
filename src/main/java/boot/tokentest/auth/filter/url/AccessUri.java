package boot.tokentest.auth.filter.url;

import org.springframework.http.HttpMethod;

public enum AccessUri {

    MEMBER_GET("/member", HttpMethod.GET),
    MEMBER_POST("/member", HttpMethod.POST),
    AUTH("/auth", HttpMethod.POST),
    LOGIN("/auth/login", HttpMethod.POST);

    private final String uriPattern;
    private final HttpMethod method;

    AccessUri(final String uriPattern, final HttpMethod method) {
        this.uriPattern = uriPattern;
        this.method = method;
    }

    public String getUriPattern() {
        return uriPattern;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public boolean matches(String uri, HttpMethod method) {
        return uri.matches(uriPattern) && this.method == method;
    }
}
