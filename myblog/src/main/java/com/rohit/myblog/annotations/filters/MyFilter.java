package com.rohit.myblog.annotations.filters;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Objects;

/*
 *    Created by rajbar[rohit.rajbanshi@dell.com] on Wednesday 11/25/2020
 *
 */
@Aspect
@Configuration
public class MyFilter {

    public static final Logger LOG = LogManager.getLogger(MyFilter.class);
    @Before("@annotation(com.rohit.myblog.annotations.CustomAnnotation) && args(request,..)")
    public void runCustomFilter(HttpServletRequest request){
        LOG.info("Running custom filter");
        if (Objects.isNull(request)) {
            throw new RuntimeException("request should be HttpServletRequest type");
        }

        LOG.info("Headers :");
        Enumeration<String> headers = request.getHeaderNames();
        while (headers.hasMoreElements()) {
            String headerName = headers.nextElement();
            LOG.info("Header:"+headerName);
            LOG.info("Value:"+request.getHeader(headerName));
        }
    }

}
