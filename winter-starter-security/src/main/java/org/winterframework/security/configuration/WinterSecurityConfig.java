package org.winterframework.security.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.winterframework.security.properties.WinterSecurityProperties;

/**
 * @author sven
 * Created on 2023/6/10 8:45 PM
 */
@Configuration
@EnableWebSecurity
public class WinterSecurityConfig {
    @Autowired
    private WinterSecurityProperties winterSecurityProperties;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return (web -> web.ignoring().requestMatchers("/favicon.ico", "/robots.txt"));
//    }

    @Bean
    @Order(1) // 重要，必须小于SecurityProperties.BASIC_AUTH_ORDER，否则此方法的配置将不生效!!!
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(authorization -> {
                    authorization
                            .requestMatchers("/favicon.ico", "/robots.txt").permitAll()
                            .requestMatchers(winterSecurityProperties.getWhitelistUrls()).permitAll()
                            .anyRequest().authenticated();
                })
                .formLogin(formLogin -> {
                    // 自定义登录页面
                    formLogin.loginPage(winterSecurityProperties.getLoginUrl()).permitAll()
                            .loginProcessingUrl(winterSecurityProperties.getLoginUrl()).permitAll();
                })
                .logout(logout -> logout.logoutUrl(winterSecurityProperties.getLogoutUrl()).permitAll())
                .build();
    }

//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration corsConfiguration = new CorsConfiguration();
//        corsConfiguration.setAllowedOriginPatterns(Lists.newArrayList());
//        corsConfiguration.setAllowedMethods(Lists.newArrayList());
//        corsConfiguration.setAllowedHeaders(Lists.newArrayList());
//        corsConfiguration.setAllowCredentials(true);
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", corsConfiguration);
//        return source;
//    }
}
