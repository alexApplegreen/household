package de.applegreen.household.web;

import de.applegreen.household.model.User;
import de.applegreen.household.persistence.UserRepository;
import de.applegreen.household.web.util.HasLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.InMemoryUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import java.util.List;

/**
 * @author [ATE] Alexander Tepe | alexander.tepe@lmis.de
 **/
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter implements HasLogger {

    private UserRepository userRepository;

    @Autowired
    public SecurityConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        InMemoryUserDetailsManagerConfigurer<AuthenticationManagerBuilder> configurer = auth.inMemoryAuthentication();

        List<User> users = this.userRepository.findAll();

        users.forEach((u) -> {
            try {
                configurer.withUser(u.getUsername()).password("{noop}" + u.getPassword()).authorities(u.getRole());
            } catch (Exception e) {
                this.logger().error(e.getMessage());
            }
        });

        configurer.withUser("admin").password("{noop}" + "admin").authorities("Admin");
    }

    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers("/signup").permitAll()
                .antMatchers( "/**").hasAnyRole("User", "Admin")
                .and()
                .formLogin()
                .loginPage("/login").permitAll()
                .passwordParameter("password")
                .usernameParameter("username")
                .defaultSuccessUrl("/");
    }
}
