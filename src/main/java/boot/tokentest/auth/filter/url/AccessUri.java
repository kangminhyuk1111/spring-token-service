package boot.tokentest.auth.filter.url;

import org.springframework.http.HttpMethod;

public enum AccessUri {

    MEMBER_GET("/member", HttpMethod.GET),
    MEMBER_POST("/member", HttpMethod.POST),
    AUTH("/auth", HttpMethod.POST),
    LOGIN("/auth/login", HttpMethod.POST);

    private final String uri;
    private final HttpMethod method;

    AccessUri(final String uri, final HttpMethod method) {
        this.uri = uri;
        this.method = method;
    }

    public String getUri() {
        return uri;
    }

    public HttpMethod getMethod() {
        return method;
    }
}
