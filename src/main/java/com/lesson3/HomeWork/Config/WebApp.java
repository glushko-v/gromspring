package com.lesson3.HomeWork.Config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;



@Configuration
public class WebApp extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[0];
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{AppConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }


//    @Override
//    public void onStartup(ServletContext servletContext) throws ServletException {
//
//        XmlWebApplicationContext context = new XmlWebApplicationContext();
//        context.setConfigLocation("/src/main/webapp/WEB-INF/web.xml");
//        servletContext.addListener(new ContextLoaderListener(context));
//
//        XmlWebApplicationContext servlet = new XmlWebApplicationContext();
//        servlet.setConfigLocation("/src/main/webapp/WEB-INF/spring-servlet.xml");
//
//        ServletRegistration.Dynamic spring = servletContext.addServlet("spring", new DispatcherServlet(servlet));
//        spring.setLoadOnStartup(1);
//        spring.addMapping("/");
//
//        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
//        context.scan("com");
//        context.register(AppConfig.class);
//        context.refresh();
////
//        DispatcherServlet servlet = new DispatcherServlet(context);
//        ServletRegistration.Dynamic spring = servletContext.addServlet("spring", servlet);
//        spring.setLoadOnStartup(1);
//        spring.addMapping("/");
//
//
//
//
//
//
//
//    }

}

