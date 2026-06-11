package com.yakuen.ceisa.config;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@MapperScan("com.yakuen.ceisa.mapper")
public class MyBatisConfig {

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public ConfigurationCustomizer mybatisConfigurationCustomizer() {
    return configuration -> configuration.setMapUnderscoreToCamelCase(true);
  }
}
