package org.example.cosmeticwebpro.security;

import lombok.AllArgsConstructor;
import org.example.cosmeticwebpro.security.auth.CustomOAuth2UserService;
import org.example.cosmeticwebpro.security.auth.HttpCookieOAuth2AuthorizationRequestRepository;
import org.example.cosmeticwebpro.security.auth.OAuth2AuthenticationFailureHandler;
import org.example.cosmeticwebpro.security.auth.OAuth2AuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration // DEFINE BEAN
@AllArgsConstructor
@EnableWebSecurity
public class SpringSecurityConfig {
  private final CustomOAuth2UserService customOAuth2UserService;
  //
  private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
  //
  private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;

  private final HttpCookieOAuth2AuthorizationRequestRepository
      httpCookieOAuth2AuthorizationRequestRepository;

  @Autowired private JwtAuthenticationFilter jwtAuthenticationFilter;

  @Bean // Define method bean
  public static PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  SecurityFilterChain config(HttpSecurity http) throws Exception {
    http.cors(Customizer.withDefaults())
        .csrf(AbstractHttpConfigurer::disable)
        .sessionManagement(
            httpSecuritySessionManagementConfigurer ->
                httpSecuritySessionManagementConfigurer.sessionCreationPolicy(
                    SessionCreationPolicy.STATELESS))
        .formLogin(AbstractHttpConfigurer::disable)
        .httpBasic(AbstractHttpConfigurer::disable)
        .exceptionHandling(
            httpSecurityExceptionHandlingConfigurer ->
                httpSecurityExceptionHandlingConfigurer.authenticationEntryPoint(
                    (request, response, authException) -> response.sendError(401)))
        .authorizeHttpRequests(
            (authorize) -> {
              authorize.requestMatchers("/api/user/**").hasRole("USER");
              authorize.requestMatchers("/api/admin/**").authenticated();
              authorize.requestMatchers("/admin/role/**").authenticated();
              authorize.requestMatchers("/admin/order/**").authenticated();
              authorize.requestMatchers("/user/admin/**").authenticated();
              authorize.anyRequest().permitAll();
            })
        //                .oauth2Login(Customizer.withDefaults());
        .oauth2Login(
            oauth2 ->
                oauth2
                    .authorizationEndpoint(
                        endpoint ->
                            endpoint
                                .baseUri("/oauth2/authorize")
                                .authorizationRequestRepository(
                                    cookieAuthorizationRequestRepository()))
                    .redirectionEndpoint(
                        redirectionEndpointConfig ->
                            redirectionEndpointConfig.baseUri("/oauth2/callback/*"))
                    .userInfoEndpoint(userInfo -> userInfo.userService(customOAuth2UserService))
                    .successHandler(oAuth2AuthenticationSuccessHandler)
                    .failureHandler(oAuth2AuthenticationFailureHandler));

    http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  // CONFIGURE AUTHENTICATION MANAGER BEAN , IT IS USED TO AUTHENTICATE USER
  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)
      throws Exception {
    return configuration.getAuthenticationManager();
  }

  @Bean
  public HttpCookieOAuth2AuthorizationRequestRepository cookieAuthorizationRequestRepository() {
    return new HttpCookieOAuth2AuthorizationRequestRepository();
  }

  // CONFIGURE CORS FOR ALL ORIGINS
  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(Arrays.asList("*"));
    configuration.setAllowedMethods(Arrays.asList("*"));
    configuration.setAllowedHeaders(Arrays.asList("*"));
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }
}
