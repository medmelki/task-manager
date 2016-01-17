package com.taskmanager.security;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Pattern;

@Component("corsFilter")
public class CORSFilter extends OncePerRequestFilter {

    private String allowHeaders = "Content-Type, X-Requested-With, accept";

    private String exposeHeaders = "Cache-Control, Content-Language, Content-Type, Expires, Last-Modified, Pragma, operationDuration";

    private String deltaSeconds = null;

    private String allowMethods = "POST, GET, PUT, DELETE, OPTIONS";

    private String pattern = "http://localhost.*";

    private Pattern regex = Pattern.compile(pattern);

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
        regex = Pattern.compile(pattern);
    }

    public void setExposeHeaders(String exposeHeaders) {
        this.exposeHeaders = exposeHeaders;
    }

    public void setAllowHeaders(String allowHeaders) {
        this.allowHeaders = allowHeaders;
    }

    public void setAllowMethods(String allowMethods) {
        this.allowMethods = allowMethods;
    }

    public void setDeltaSeconds(String deltaSeconds) {
        this.deltaSeconds = deltaSeconds;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String origin = request.getHeader("Origin");
//        if (origin != null && regex.matcher(origin).matches()) {
            response.addHeader("Access-Control-Allow-Origin", origin);
            response.addHeader("Access-Control-Allow-Headers", allowHeaders);
            response.addHeader("Access-Control-Expose-Headers", exposeHeaders);
            response.addHeader("Access-Control-Allow-Methods", allowMethods);
            if (deltaSeconds != null) {
                response.addHeader("Access-Control-Max-Age", deltaSeconds);
            }
            response.addHeader("Access-Control-Allow-Credentials", "true");
//        }
        filterChain.doFilter(request, response);
    }
}
