package com.envyleague.cricket.web.filter;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class CachingHttpHeaderFilter implements Filter {

    // Cache period is 1 month (in ms)
    private final static long CACHE_PERIOD = TimeUnit.DAYS.toMillis(31L);

    // We consider the last modified date is the start up time of the server
    private final static long LAST_MODIFIED = System.currentTimeMillis();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //Nothing to init
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        httpResponse.setHeader("Cache-Control", "no-cache");
        httpResponse.setHeader("Pragma", "no-cache");

        //*For Production
        //httpResponse.setHeader("Cache-Control", "max-age=2678400000, public");
        //httpResponse.setHeader("Pragma", "cache");

        // Setting Expires header, for proxy caching
        //httpResponse.setDateHeader("Expires", CACHE_PERIOD + System.currentTimeMillis());

        // Setting the Last-Modified header, for browser caching
        //httpResponse.setDateHeader("Last-Modified", LAST_MODIFIED);*/

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        //noting to destroy
    }
}
