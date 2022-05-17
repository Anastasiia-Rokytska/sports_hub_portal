package com.company.sportHubPortal.Security;

import com.company.sportHubPortal.Database.UserRole;
import com.company.sportHubPortal.Services.JwtTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    final CustomUserDetailsService userDetailsService;
    final PasswordEncoder passwordEncoder;
    final JwtTokenService jwtTokenService;

    @Autowired
    public SecurityConfig(CustomUserDetailsService userDetailsService,
                          PasswordEncoder passwordEncoder,
                          JwtTokenService jwtTokenService) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenService = jwtTokenService;
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        CustomAuthenticationFilter authenticationFilter = new CustomAuthenticationFilter(
                authenticationManagerBean(),
                jwtTokenService,
                userDetailsService);
        authenticationFilter.setFilterProcessesUrl("/user/login");

        CustomAuthorizationFilter authorizationFilter = new CustomAuthorizationFilter(
                jwtTokenService,
                authenticationManagerBean(),
                userDetailsService);

        httpSecurity.cors().disable();
        httpSecurity.csrf().disable();

        httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);





        httpSecurity.authorizeRequests()
                .antMatchers("/user/own_information", "/personal_page").authenticated()
                .and()
                .addFilterAfter(authorizationFilter, authenticationFilter.getClass());

        httpSecurity
                .formLogin()
                .loginPage("/login")
                .usernameParameter("email")
                .permitAll()
                .and().addFilter(authenticationFilter);



        httpSecurity
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .deleteCookies("access_token")
                .deleteCookies("refresh_token");

    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/assets/**");
    }


    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }
}
