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
        allowUriList.put(AccessUri.MEMBER_GET.getUriPattern(), AccessUri.MEMBER_GET.getMethod());
        allowUriList.put(AccessUri.MEMBER_POST.getUriPattern(), AccessUri.MEMBER_POST.getMethod());
        allowUriList.put(AccessUri.AUTH.getUriPattern(), AccessUri.AUTH.getMethod());
        allowUriList.put(AccessUri.LOGIN.getUriPattern(), AccessUri.LOGIN.getMethod());
    }

    public boolean isAvailableUri(String uri, HttpMethod method) {
        for (AccessUri accessUri : AccessUri.values()) {
            if (accessUri.matches(uri, method)) {
                return true;
            }
        }
        return false;
    }
}
