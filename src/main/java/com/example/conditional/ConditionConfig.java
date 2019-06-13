package com.example.conditional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConditionConfig {
    /**
     * @Condition 在符合某个条件下创建Bean
     * @return
     */
    @Bean
    @Conditional(WindowsCondition.class)
    public ListService windowServiceBean () {
        return new WindowServiceBean();
    }
    @Bean
    @Conditional(LinuxCondition.class)
    public ListService linuxServiceBean () {
        return new LinuxServiceBean();
    }
}
