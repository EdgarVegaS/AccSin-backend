package com.accsin;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringAppContext implements ApplicationContextAware {

    private static ApplicationContext CONTEXT;

    @Override
    public void setApplicationContext(ApplicationContext arg0) throws BeansException {
        CONTEXT = arg0;
    }

    public static Object getBean(String beanName) {
        return CONTEXT.getBean(beanName);
    }
}
