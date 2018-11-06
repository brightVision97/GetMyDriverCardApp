package com.rachev.getmydrivercardbackend.security;

import com.rachev.getmydrivercardbackend.repositories.UsersRepository;
import com.rachev.getmydrivercardbackend.services.base.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
@EnableJpaRepositories(basePackageClasses = UsersRepository.class)
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter
{
    private final UsersService usersService;
    
    @Autowired
    public SpringSecurityConfig(UsersService usersService)
    {
        this.usersService = usersService;
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http
                .authorizeRequests()
                .antMatchers("**/secured/**")
                .hasRole("ADMIN")
                .anyRequest()
                .hasRole("USER")
                .and()
                .httpBasic();
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception
    {
        auth.userDetailsService(usersService)
                .passwordEncoder(getPasswordEncoder());
    }
    
    @Override
    public void configure(WebSecurity web)
    {
        web.ignoring()
                .antMatchers("/api/users/signup")
                .antMatchers("/pushnotify/send");
    }
    
    private PasswordEncoder getPasswordEncoder()
    {
        return new BCryptPasswordEncoder();
    }
}
