package com.br.lsolution.financialcontrol.api.config.exception;

import com.br.lsolution.financialcontrol.api.service.JwtService;
import com.br.lsolution.financialcontrol.api.service.SecurityUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.List;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private SecurityUserDetailsService userDetailsService;
    @Autowired
    private JwtService jwtService;

    @Autowired
    private CorsFilter corsFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder;
    }

    @Bean
    public JwtTokenFilter jwtTokenFilter() {
        return new JwtTokenFilter(jwtService, userDetailsService);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .antMatchers(HttpMethod.GET, "/actuator/**").permitAll()
                .antMatchers(HttpMethod.POST, "/users/autenticar").permitAll()
                .antMatchers(HttpMethod.POST, "/users").permitAll()
                .antMatchers(HttpMethod.GET, "/users/email/**").permitAll()
                .antMatchers(HttpMethod.POST, "/users/email").permitAll()
                .antMatchers(HttpMethod.POST, "/users/existe").permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore( corsFilter, UsernamePasswordAuthenticationFilter.class )
                .addFilterBefore( jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class )
        ;

    }

//    @Bean
//    public FilterRegistrationBean<CorsFilter> corsFilter(){
//
//        List<String> all = Arrays.asList("*");
//
//        CorsConfiguration config = new CorsConfiguration();
//        config.setAllowedMethods(all);
//        config.setAllowedOrigins(all);
//        config.setAllowedHeaders(all);
//        config.setAllowCredentials(true);
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", config);
//
//        CorsFilter corFilter = new CorsFilter(source);
//
//        FilterRegistrationBean<CorsFilter> filter =
//                new FilterRegistrationBean<CorsFilter>(corFilter);
//        filter.setOrder(Ordered.HIGHEST_PRECEDENCE);
//
//        return filter;
//    }




}
