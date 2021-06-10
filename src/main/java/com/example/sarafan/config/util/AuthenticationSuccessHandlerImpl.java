package com.example.sarafan.config.util;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

/**
 * @author Ivan Kurilov on 09.06.2021
 */
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        addSameSiteCookieAttribute(response);    // add SameSite=none to Set-Cookie attribute
        response.sendRedirect("/index"); // redirect to hello.html after success auth
    }

    private void addSameSiteCookieAttribute(HttpServletResponse response) {
        Collection<String> headers = response.getHeaders(HttpHeaders.SET_COOKIE);
        boolean firstHeader = true;
        for (String header : headers) { // there can be multiple Set-Cookie attributes
            if (firstHeader) {
                response.setHeader(HttpHeaders.SET_COOKIE, String.format("%s; %s", header, "SameSite=None"));
                firstHeader = false;
                continue;
            }
            response.addHeader(HttpHeaders.SET_COOKIE, String.format("%s; %s", header, "SameSite=None"));
        }
    }
}
