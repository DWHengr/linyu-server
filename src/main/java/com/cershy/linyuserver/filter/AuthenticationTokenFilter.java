package com.cershy.linyuserver.filter;

import com.cershy.linyuserver.utils.JwtUtil;
import com.cershy.linyuserver.utils.ResultUtil;
import com.cershy.linyuserver.utils.UrlPermitUtil;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: dwh
 **/
@Component
public class AuthenticationTokenFilter extends OncePerRequestFilter {

    private final String TokenName = "x-token";

    @Resource
    private UrlPermitUtil urlPermitUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        if ("OPTIONS".equalsIgnoreCase(httpServletRequest.getMethod())) {
            return;
        }

        String token = httpServletRequest.getHeader(TokenName);
        String url = httpServletRequest.getRequestURI();
        // 验证url是否需要验证
        if (!urlPermitUtil.isPermitUrl(url)) {
            Claims claims = null;
            try {
                claims = JwtUtil.parseToken(token);
            } catch (Exception e) {
                httpServletResponse.setContentType("application/json;charset=UTF-8");
                httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
                PrintWriter out = httpServletResponse.getWriter();
                out.write(ResultUtil.TokenInvalid().toJSONString(0));
                out.flush();
                out.close();
                return;
            }
            // 设置用户信息
            Map<String, Object> map = new HashMap<>();
            claims.entrySet().stream().forEach(e -> map.put(e.getKey(), e.getValue()));
            httpServletRequest.setAttribute("userinfo", map);
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
