package com.example.crud.security;

import com.example.crud.service.StudentService;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

    private final StudentService studentService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public WebSecurity(StudentService studentService, BCryptPasswordEncoder bCryptPasswordEncoder){
        this.studentService=studentService;
        this.bCryptPasswordEncoder=bCryptPasswordEncoder;
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(studentService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                //.antMatchers(HttpMethod.POST, SecurityConstants.SIGN_UP_URL)
                .antMatchers(SecurityConstants.SIGN_UP_URL, SecurityConstants.STUDENT_LIST_URL)
                .permitAll().anyRequest().authenticated().and()
                //.and().addFilter(new AuthenticationFilter(authenticationManager()));
                .addFilter(getAuthenticationFilter())
                .addFilter(new AuthorizationFilter(authenticationManager()))
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    public AuthenticationFilter getAuthenticationFilter() throws Exception {
        final AuthenticationFilter authenticationFilter = new AuthenticationFilter(authenticationManager());
        authenticationFilter.setFilterProcessesUrl("/api/v1/login");
        return authenticationFilter;
    }
}
