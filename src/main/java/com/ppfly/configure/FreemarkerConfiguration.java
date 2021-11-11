package com.ppfly.configure;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.TemplateExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class FreemarkerConfiguration {

    @Bean
    public freemarker.template.Configuration freemarkerConfig() throws IOException {
        freemarker.template.Configuration cfg = new freemarker.template.Configuration(freemarker.template.Configuration.getVersion());
        //In spring boot you cannot use the normal File approach to get your templates
        // because they are not accessible when you run an executable JAR (File cannot be loaded as a resource when inside the JAR)
        cfg.setTemplateLoader(new ClassTemplateLoader(super.getClass().getClassLoader(), "templates"));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(false);
        return cfg;
    }
}