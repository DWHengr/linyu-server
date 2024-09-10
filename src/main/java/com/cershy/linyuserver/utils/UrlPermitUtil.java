package com.cershy.linyuserver.utils;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: dwh
 **/
@Component
public class UrlPermitUtil {
    // 免验证Url
    private List<String> urls = new ArrayList<>();
    // 需要验证角色的url资源
    private Map<String, List<String>> roleUrl = new HashMap<>();

    {
        urls.add("/ws/**");
    }

    public boolean verifyUrl(String permitUrl, List<String> urlArr) {
        for (String url : urlArr) {
            for (int index = 0; index < url.length(); index++) {
                if (url.charAt(index) == '*') {
                    return true;
                }
                if (permitUrl.length() == index + 1 && url.length() == index + 1) {
                    return true;
                }
                if (index == permitUrl.length() || permitUrl.charAt(index) != url.charAt(index)) {
                    break;
                }
            }
        }
        return false;
    }

    public boolean isPermitUrl(String url) {
        return verifyUrl(url, urls);
    }


    public List<String> getPermitAllUrl() {
        return urls;
    }

    public void addUrls(List<String> urls) {
        this.urls.addAll(urls);
    }

    public void addRoleUrl(String role, String url) {
        List<String> roles = roleUrl.get(url);
        if (roles == null) {
            roles = new ArrayList<>();
            roleUrl.put(url, roles);
        }
        roles.add(role);
    }

    public boolean isRoleUrl(String role, String url) {
        List<String> roles = roleUrl.get(url);
        if (roles == null) return true;
        for (String r : roles) {
            if (r.equals(role)) {
                return true;
            }
        }
        return false;
    }
}
