package com.haneul.study.springboot.config.JpaConfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing    // JPA Auditing 활성화
public class JpaConfig {
}
