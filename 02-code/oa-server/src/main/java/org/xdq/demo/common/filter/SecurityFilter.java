package org.xdq.demo.common.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.util.AntPathMatcher;
import org.xdq.demo.common.OaConstants;
import org.xdq.demo.common.config.AnonUrls;
import org.xdq.demo.common.currentuser.CurrentUser;
import org.xdq.demo.common.ex.UnauthencationException;
import org.xdq.demo.common.token.TokenUser;
import org.xdq.demo.common.token.TokenUtils;
import org.xdq.demo.common.vo.R;

import java.io.IOException;
import java.io.PrintWriter;

@RequiredArgsConstructor
public class SecurityFilter implements Filter {

    private final TokenUtils tokenUtils;
    private final AnonUrls anonUrls;




    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;


        if(anonUrls != null && anonUrls.getAnonUrls()!=null){
            AntPathMatcher matcher = new AntPathMatcher();

            boolean match = anonUrls.getAnonUrls().stream().anyMatch(item -> matcher.match(item, request.getServletPath()));
            if(match){
                filterChain.doFilter(request,servletResponse);
                return;
            }
        }

        String userIp = request.getRemoteAddr();
        String token = request.getHeader(OaConstants.REQUEST_HEADER_NAME_TOKEN);

        try {
            TokenUser tokenUser = tokenUtils.verify(token, userIp);
            servletRequest.setAttribute(OaConstants.currentUserName,new CurrentUser(tokenUser.getUserId(),tokenUser.getUserName()));
            filterChain.doFilter(request,servletResponse);
        } catch (UnauthencationException e) {
            HttpServletResponse response = (HttpServletResponse)servletResponse;
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(R.err(R.CODE_ERR_AUTHENTICATION, e.getMessage()));
            PrintWriter out = response.getWriter();
            out.print(json);
            out.flush();
            out.close();


        }
    }
}
