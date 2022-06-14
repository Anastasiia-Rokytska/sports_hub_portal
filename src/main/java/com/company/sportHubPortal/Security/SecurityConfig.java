package com.company.sportHubPortal.Security;

import com.company.sportHubPortal.Database.CustomOAuth2User;
import com.company.sportHubPortal.Database.User;
import com.company.sportHubPortal.Services.JwtTokenService;
import com.company.sportHubPortal.Services.UserService;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;


@Configuration
@EnableWebSecurity
@PropertySource("classpath:application.properties")

public class SecurityConfig extends WebSecurityConfigurerAdapter {

 private final CustomUserDetailsService userDetailsService;
 private final PasswordEncoder passwordEncoder;
 private final JwtTokenService jwtTokenService;
  private final Environment env;
  private final CustomOAuth2UserService oauth2UserService;
  private final UserService userService;

  @Autowired
  public SecurityConfig(CustomUserDetailsService userDetailsService,
                        PasswordEncoder passwordEncoder,
                        JwtTokenService jwtTokenService,
                        Environment env,
                        CustomOAuth2UserService oauth2UserService,
                        UserService userService) {
    this.userDetailsService = userDetailsService;
    this.passwordEncoder = passwordEncoder;
    this.jwtTokenService = jwtTokenService;
    this.env = env;
    this.oauth2UserService = oauth2UserService;
    this.userService = userService;
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

    //httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

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
        .defaultSuccessUrl("/user/oauthSuccess")
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


  }

  @Override
  public void configure(WebSecurity web) {
    web.ignoring().antMatchers("/assets/**", "/user/verify/**");
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


  private static List<String> clients = Arrays.asList("google", "facebook");

  @Bean
  public ClientRegistrationRepository clientRegistrationRepository() {
    List<ClientRegistration> registrations = clients.stream()
        .map(c -> getRegistration(c))
        .filter(registration -> registration != null)
        .collect(Collectors.toList());
    return new InMemoryClientRegistrationRepository(registrations);
  }

  private static String CLIENT_PROPERTY_KEY
      = "spring.security.oauth2.client.registration.";

  private ClientRegistration getRegistration(String client) {
    String clientId = env.getProperty(
        CLIENT_PROPERTY_KEY + client + ".client-id");

    if (clientId == null) {
      return null;
    }

    String clientSecret = env.getProperty(
        CLIENT_PROPERTY_KEY + client + ".client-secret");

    if (client.equals("google")) {
      return CommonOAuth2Provider.GOOGLE.getBuilder(client)
          .clientId(clientId).clientSecret(clientSecret).build();
    }
    if (client.equals("facebook")) {
      return CommonOAuth2Provider.FACEBOOK.getBuilder(client)
          .clientId(clientId).clientSecret(clientSecret).build();
    }
    return null;
  }

  @Bean
  public OAuth2AuthorizedClientService authorizedClientService() {

    return new InMemoryOAuth2AuthorizedClientService(
        clientRegistrationRepository());
  }


}
