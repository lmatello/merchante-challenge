package com.merchante.utils;

import com.merchante.pages.selenium.common.Page;
import com.google.common.collect.ImmutableMap;
import org.springframework.core.env.Environment;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Map;

public class ThymeleaftPageUrlBuilder {

    public static String urlFor(Class<? extends Page> pageClass, Environment environment, Map pageContextMap) {
        try {
            PageUrl pageUrl = pageClass.getAnnotation(PageUrl.class);
            String urlTemplate = pageUrl.url();
            String hostTemplate = urlTemplate.split("/")[0];
            String endPointSolvedUrl = urlTemplate.replace(hostTemplate, environment.getProperty(hostTemplate));
            TemplateEngine templateEngine = new TemplateEngine();
            Context context = new Context(null, ImmutableMap.of("pc", pageContextMap));
            return templateEngine.process(endPointSolvedUrl, context);
        } catch (Exception e) {
            throw new IllegalStateException("Fail on PageObject URL");
        }
    }
}


