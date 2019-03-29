package com.example.el;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ELConfig.class);
        ELConfig config = context.getBean(ELConfig.class);
        System.out.println(config);
        context.close();
    }
}
