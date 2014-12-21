package com.envyleague.cricket.config;

import com.envyleague.cricket.web.filter.SimpleCORSFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.ServletContextInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.inject.Inject;
import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.Arrays;
import java.util.EnumSet;

@Configuration
public class WebConfig implements ServletContextInitializer, EmbeddedServletContainerCustomizer {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Inject
    private Environment env;

    @Override
    public void customize(ConfigurableEmbeddedServletContainer configurableEmbeddedServletContainer) {

    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        log.info("Web application configuration, using profiles: {}", Arrays.toString(env.getActiveProfiles()));
        EnumSet<DispatcherType> disps = EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.ASYNC);
        FilterRegistration.Dynamic corsFilter = servletContext.addFilter("corsFilter", new SimpleCORSFilter());
        corsFilter.addMappingForUrlPatterns(disps, true, "/*");
        corsFilter.setAsyncSupported(true);
    }
}
