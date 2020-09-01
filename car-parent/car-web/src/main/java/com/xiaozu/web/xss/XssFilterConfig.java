package com.xiaozu.web.xss;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author zheye
 * @Date 2020/7/17 0017 16:00
 * @Version 1.0
 */
@Configuration
public class XssFilterConfig {

    /**
     * 开启xss验证
     */
    @Value("${xss.enabled:true}")
    private String enabled;

    @Value("${xss.excludes:}")
    private String excludes;

    /**
     * 不过滤的链接
     */
    @Value("${xss.excludeUrls:/statics}")
    private String excludeUrls;

    /**
     * 允许的标签: a, b, blockquote, br, caption, cite, code, col, colgroup, dd, dl, dt, em,
     * h1, h2, h3, h4, h5, h6, i, img, li, ol, p, pre, q, small, strike, strong, sub, sup,
     * table, tbody, td, tfoot, th, thead, tr, u, ul。结果不包含标签rel=nofollow ，如果需要可以手动添加。
     */
    @Value("${xss.includes:a}")
    private String includes;

    @Value("${xss.urlPatterns:/*}")
    private String urlPatterns;

    @Bean
    public FilterRegistrationBean<XssFilter> xssFilterRegistrationBean() {
        FilterRegistrationBean<XssFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new XssFilter());
        registration.addUrlPatterns(urlPatterns.split(","));
        registration.setName("XssFilter");
        registration.setOrder(Integer.MAX_VALUE);
        Map<String, String> initParameters = new HashMap<String, String>();
        initParameters.put("excludeUrls", excludeUrls);
        initParameters.put("excludeTagStr", excludes);
        initParameters.put("includeTagStr", includes);
        initParameters.put("enabled", enabled);
        registration.setInitParameters(initParameters);
        return registration;
    }

}
