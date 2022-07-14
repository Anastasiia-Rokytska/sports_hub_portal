package com.company.sportHubPortal.Security;

import com.company.sportHubPortal.Controllers.UserController;
import com.company.sportHubPortal.Services.JwtTokenService;
import com.company.sportHubPortal.Services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
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
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.header.writers.StaticHeadersWriter;


@Configuration
@EnableWebSecurity
@PropertySource("classpath:application.properties")

public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final CustomUserDetailsService userDetailsService;
  private final PasswordEncoder passwordEncoder;
  private final JwtTokenService jwtTokenService;
  private final OAuthLoginSuccessHandler oAuthLoginSuccessHandler;
  Logger logger = LoggerFactory.getLogger(UserController.class);

  @Autowired
  public SecurityConfig(CustomUserDetailsService userDetailsService,
                        PasswordEncoder passwordEncoder,
                        JwtTokenService jwtTokenService,
                        OAuthLoginSuccessHandler oAuthLoginSuccessHandler) {
    this.userDetailsService = userDetailsService;
    this.passwordEncoder = passwordEncoder;
    this.jwtTokenService = jwtTokenService;
    this.oAuthLoginSuccessHandler = oAuthLoginSuccessHandler;
  }


  @Override
  protected void configure(HttpSecurity httpSecurity) throws Exception {
    httpSecurity.headers().addHeaderWriter(
            new StaticHeadersWriter("Access-Control-Allow-Origin", "*"));

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
        .and()
        .oauth2Login()
        .loginPage("/login")
        .successHandler(oAuthLoginSuccessHandler)
        .failureHandler((request, response, exception) ->
            logger.info(exception.toString()))
        .and().addFilter(authenticationFilter);

    httpSecurity
        .logout()
        .logoutUrl("/logout")
        .logoutSuccessUrl("/login")
        .deleteCookies("access_token")
        .deleteCookies("refresh_token");

    httpSecurity.authorizeRequests()
        .antMatchers("/admin/**").hasAuthority("ADMIN")
        .and()
        .exceptionHandling().accessDeniedHandler(accessDeniedHandler());

    httpSecurity.authorizeRequests()
            .antMatchers(HttpMethod.POST, "/team").hasAuthority("ADMIN")
            .and()
            .exceptionHandling().accessDeniedHandler(accessDeniedHandler());
  }


  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
  }

  @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Bean
  public AccessDeniedHandler accessDeniedHandler() {
    return new CustomAccessDeniedHandler();
  }
}
