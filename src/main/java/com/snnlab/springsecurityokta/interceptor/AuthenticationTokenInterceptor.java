package com.snnlab.springsecurityokta.interceptor;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class AuthenticationTokenInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)  {
        JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        anlyzeToken(jwtAuthenticationToken);
        checkRequestToken(request.getHeader("Authorization"),jwtAuthenticationToken);
        return true;
    }

    private void checkRequestToken(String authorizationHeaderValue, JwtAuthenticationToken jwtAuthenticationToken) {
        if(StringUtils.hasText(authorizationHeaderValue)) {
            Jwt jwt = (Jwt) jwtAuthenticationToken.getCredentials();
            var idToken = authorizationHeaderValue.split(" ")[1];
            if(!idToken.equals(jwt.getTokenValue())){
                throw new RuntimeException("The token in request header and the token in security context is not equal.");
            }
        }
    }

    private void anlyzeToken( JwtAuthenticationToken jwtAuthenticationToken ) {
        Jwt jwt = (Jwt) jwtAuthenticationToken.getCredentials();
        //OpenId Token Structure includes Headers/Claims
        //Headers (kid,alg)
        jwt.getHeaders().entrySet().stream().forEach(e -> System.out.println(e.getKey() + "=" + e.getValue()));
        //Claims (aud,uid,scp,sub,ver,iss,exp,iat,jti,cid)
        jwt.getClaims().entrySet().stream().forEach(e -> System.out.println(e.getKey() + "=" + e.getValue()));
    }
}



