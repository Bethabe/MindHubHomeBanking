package com.mindhub.homebanking.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@EnableWebSecurity
@Configuration
public class WebAuthorization {
    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/web/index.html","/web/js/index.js", "/web/css/**", "/web/img/**").permitAll()
                .antMatchers(HttpMethod.POST, "/web/**","/api/loans", "/api/transactions", "/manager.js","/manager.html","/api/clients/**").hasAnyAuthority("CLIENT")
                .antMatchers(HttpMethod.GET,"/web/**", "/api/loans", "/manager.js","/api/transactions", "/manager.html","/api/clients/**").hasAnyAuthority("CLIENT")
                .antMatchers(HttpMethod.GET, "/api/clients","/rest/**","/h2-console/**").hasAnyAuthority("ADMIN")
                .anyRequest().denyAll();
        //.antMatchers("/api/**").permitAll();

        http.formLogin()
                .usernameParameter("email")
                .passwordParameter("password")
                .loginPage("/api/login");
        http.logout().logoutUrl("/api/logout");
        http.headers().frameOptions().disable();
        http.csrf().disable();
        http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));
        http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));
        http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));
        http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());
        return http.build();
    }
    private void clearAuthenticationAttributes(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }
    }
}
