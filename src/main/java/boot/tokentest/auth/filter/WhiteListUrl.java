package boot.tokentest.auth.filter;

import boot.tokentest.auth.filter.url.AccessUri;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class WhiteListUrl {

    private final Map<String, HttpMethod> allowUriList;

    public WhiteListUrl() {
        allowUriList = new HashMap<>();
        allowUriList.put(AccessUri.MEMBER_GET.getUri(), AccessUri.MEMBER_GET.getMethod());
        allowUriList.put(AccessUri.MEMBER_POST.getUri(), AccessUri.MEMBER_POST.getMethod());
        allowUriList.put(AccessUri.AUTH.getUri(), AccessUri.AUTH.getMethod());
        allowUriList.put(AccessUri.LOGIN.getUri(), AccessUri.LOGIN.getMethod());
    }

    public boolean isAvailableUri(String uri, HttpMethod httpMethod) {
        return allowUriList.containsKey(uri) && allowUriList.get(uri).equals(httpMethod);
    }
}
