package com.merchante.utils;

import com.merchante.pages.selenium.common.Page;
import org.springframework.core.env.Environment;
import java.util.HashMap;
import java.util.Map;


public class PageUrlUtils {

    public static String urlFor(Class<? extends Page> pageClass, Environment environment, Map pageContext) {
        return ThymeleaftPageUrlBuilder.urlFor(pageClass, environment, pageContext);
    }

    public static String urlFor(Class<? extends Page> pageClass, Environment environment) {
        return urlFor(pageClass, environment, new HashMap());
    }
}
