package br.com.zupacademy.matheus.propostas.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests(authorizeRequests ->
                authorizeRequests
                        .antMatchers(HttpMethod.GET, "/propostas/**").hasAuthority("SCOPE_propostas")
                        .antMatchers(HttpMethod.GET, "/biometrias/**").hasAuthority("SCOPE_propostas")
                        .antMatchers(HttpMethod.POST, "/propostas/**").hasAuthority("SCOPE_propostas")
                        .antMatchers(HttpMethod.POST, "/biometrias/**").hasAuthority("SCOPE_propostas")
                        .antMatchers(HttpMethod.GET, "/actuator/**").hasAuthority("SCOPE_propostas")
                        .antMatchers(HttpMethod.GET, "/bloqueios/**").hasAuthority("SCOPE_propostas")
                        .antMatchers(HttpMethod.POST, "/aviso-viagem/**").hasAuthority("SCOPE_propostas")
                        .anyRequest().authenticated()
        )
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
    }
}