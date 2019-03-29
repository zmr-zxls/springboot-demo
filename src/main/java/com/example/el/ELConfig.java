package com.example.el;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;

@Configuration
@ComponentScan("com.example.el")
@PropertySource("classpath:/com/example/el/test.properties")
public class ELConfig {
    @Value("I Love You")
    private String normal;

    @Value("#{ systemProperties['os.name'] }")
    private String osName;

    @Value("#{ T(java.lang.Math).random() * 100.0 }")
    private double randomNumber;

    @Value("${book.name}")
    private String bookName;

    @Autowired
    private Environment environment;

    @Override
    public String toString() {
        return "ELConfig{" +
                "normal='" + normal + '\'' +
                ", osName='" + osName + '\'' +
                ", randomNumber=" + randomNumber +
                ", bookName='" + bookName + '\'' +
                ", environment=" + environment +
                '}';
    }
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigure() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
