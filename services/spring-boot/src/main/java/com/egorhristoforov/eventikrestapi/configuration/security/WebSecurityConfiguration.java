package com.egorhristoforov.eventikrestapi.configuration.security;

import com.egorhristoforov.eventikrestapi.configuration.jwt.JwtAuthenticationEntryPoint;
import com.egorhristoforov.eventikrestapi.configuration.jwt.JwtAuthenticationFilter;
import com.egorhristoforov.eventikrestapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    private static final String ADMIN_ENDPOINT = "/api/v1/admin/**";

    @Autowired
    UserService userService;

    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Autowired
    public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService)
                .passwordEncoder(bCryptPasswordEncoder());
    }

    @Bean
    public JwtAuthenticationFilter authenticationTokenFilterBean() {
        return new JwtAuthenticationFilter();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors()
                .and()
                .csrf().disable()
                .authorizeRequests()
                    .antMatchers(HttpMethod.GET, "/swagger").permitAll()
                    .antMatchers(HttpMethod.POST, "/api/v1/users/**").permitAll()
                    .antMatchers(HttpMethod.POST, "/api/v1/auth/**").permitAll()
                    .antMatchers(HttpMethod.GET, "/api/v1/fields").permitAll()
                    .antMatchers("/api/v1/fields/**").hasAnyRole("ADMIN", "MODERATOR")
                    .antMatchers(HttpMethod.GET, "/api/v1/countries/**").permitAll()
                    .antMatchers(HttpMethod.GET, "/api/v1/events/**/news").authenticated()
                    .antMatchers(HttpMethod.GET, "/api/v1/events/**/chat").authenticated()
                    .antMatchers(HttpMethod.GET, "/api/v1/events/**/visitors/blocked").authenticated()
                    .antMatchers(HttpMethod.GET, "/api/v1/events/**/status").authenticated()
                    .antMatchers(HttpMethod.GET, "/api/v1/events/**").permitAll()
                    .antMatchers(HttpMethod.POST, "/api/v1/admin/login").permitAll()
                    .antMatchers(ADMIN_ENDPOINT).hasAnyRole("ADMIN", "MODERATOR")
                    .anyRequest().authenticated()
                .and()
                .addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder());
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**");
    }
}
