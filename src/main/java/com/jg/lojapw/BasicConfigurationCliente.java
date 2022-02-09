package com.jg.lojapw;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@Order(1)
public class BasicConfigurationCliente extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource).usersByUsernameQuery(
                "select email as username, senha as password, 1 as enable from cliente where email=?"
        ).authoritiesByUsernameQuery(
                "select email as username, 'cliente' as authority from cliente where email=?"
        ).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/finalizar/**").authorizeRequests().anyRequest().hasAnyAuthority("cliente")
                .and().csrf().disable().formLogin().loginPage("/cliente/cadastrar").permitAll()
                .failureUrl("/cliente/cadastrar").loginProcessingUrl("/finalizar/login")
                .defaultSuccessUrl("/finalizar")
                .usernameParameter("username").passwordParameter("password")
                .and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/finalizar/logout"))
                .logoutSuccessUrl("/").permitAll().and().exceptionHandling().accessDeniedPage("/negadoCliente");
    }


}