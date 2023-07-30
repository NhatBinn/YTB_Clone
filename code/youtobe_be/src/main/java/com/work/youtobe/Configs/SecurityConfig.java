package com.work.youtobe.Configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SecurityConfig implements WebMvcConfigurer {

     @Bean
     public WebSecurityCustomizer webSecurityCustomizer() {
          return (web) -> web.ignoring()
                    .requestMatchers("/**");
     }

     @Override
     public void addCorsMappings(CorsRegistry registry) {
          registry
                    .addMapping("/**")
                    .allowedMethods("GET", "POST", "PUT", "DELETE");
     }
}
