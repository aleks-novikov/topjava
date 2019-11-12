package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringContextFactory {

    private static ConfigurableApplicationContext appCtx;

    public static ConfigurableApplicationContext getContext() {
        if (appCtx == null) {
            appCtx = initSpringContext();
        }
        return appCtx;
    }

    private static ConfigurableApplicationContext initSpringContext() {
        return new ClassPathXmlApplicationContext("spring/spring-app.xml");
    }
}
