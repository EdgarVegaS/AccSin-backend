package com.accsin.security;


import com.accsin.services.interfaces.UserServiceInterface;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

    private final UserServiceInterface userService;
    private final BCryptPasswordEncoder bCrypt;

    public WebSecurity(UserServiceInterface userService, BCryptPasswordEncoder bCrypt) {
        this.userService = userService;
        this.bCrypt = bCrypt;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
        .antMatchers("/v2/api-docs",
                    "/configuration/ui",
                    "/swagger-resources/**",
                    "/configuration/security",
                    "/swagger-ui.html",
                    "/webjars/**").permitAll()
        .antMatchers(HttpMethod.POST, "/users").permitAll()
        .antMatchers(HttpMethod.POST, "/services").permitAll()
        .antMatchers(HttpMethod.POST, "/checklists").permitAll()
        .antMatchers(HttpMethod.GET, "/menu/*").permitAll()
        .antMatchers(HttpMethod.GET, "/actionTypes/*").permitAll()
        .antMatchers(HttpMethod.POST, "/actionTypes/*").permitAll()
        .antMatchers(HttpMethod.PUT).permitAll()
        .anyRequest().authenticated().and().addFilter(getAuthenticationFilter())
        .addFilter(new AuthorizationFilter(authenticationManager()))
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(userService).passwordEncoder(bCrypt);
    }

    public AuthenticationFilter getAuthenticationFilter() throws Exception{
        final AuthenticationFilter filter = new AuthenticationFilter(authenticationManager(), new ModelMapper());

        filter.setFilterProcessesUrl("/users/login");
        return filter;
    }

}
