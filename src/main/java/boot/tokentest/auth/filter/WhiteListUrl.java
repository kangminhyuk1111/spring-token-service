package boot.tokentest.auth.filter;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class WhiteListUrl {

    private final Map<String, HttpMethod> allowUriList;

    public WhiteListUrl() {
        allowUriList = new HashMap<>();
        allowUriList.put("/member", HttpMethod.POST);
        allowUriList.put("/auth", HttpMethod.POST);
        allowUriList.put("/auth/accessToken", HttpMethod.POST);
    }

    public boolean isAvailableUri(String uri, HttpMethod httpMethod) {
        return allowUriList.containsKey(uri) && allowUriList.get(uri).equals(httpMethod);
    }
}
