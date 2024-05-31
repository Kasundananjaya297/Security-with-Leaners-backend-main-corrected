package com.example.SecuritywithLeaners.Config;

import com.example.SecuritywithLeaners.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
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

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserService ourUserDetailsService;
    @Autowired
    private JWTAuthFilter jwtAuthFIlter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(request ->
                        request.requestMatchers("/api/authentication/**").permitAll()
                                .requestMatchers("/api/admin/getAllPackage/*/*/*/*").permitAll()
                        .requestMatchers("/api/admin/getStudentByID/*").hasAnyAuthority("STUDENT","ADMIN")
                        .requestMatchers("/api/admin/getPayments/*/*").hasAnyAuthority("ADMIN","STUDENT")
                                .requestMatchers("/api/admin/getAllnotificationByID/*/*").hasAnyAuthority("TRAINER","STUDENT")
                                .requestMatchers("/api/admin/getAllNotifications/*").hasAnyAuthority("ADMIN","STUDENT","TRAINER")
                                .requestMatchers("/api/admin/deleteNotification/*").hasAnyAuthority("ADMIN","STUDENT","TRAINER")
                        .requestMatchers("/api/admin/getAgreement/*").hasAnyAuthority("ADMIN","STUDENT")
                        .requestMatchers("/api/admin/getTrainerByID/*").hasAnyAuthority("TRAINER","ADMIN")
                                .requestMatchers("/api/admin/getExtraSessionNotINAgreement/*/*").hasAnyAuthority("ADMIN","STUDENT")
                        .requestMatchers("/api/admin/**").hasAuthority("ADMIN")
                        .requestMatchers("/api/student/studentCancelBooking/*/*").hasAnyAuthority( "STUDENT","ADMIN")
                        .requestMatchers("/api/student/changePassword").hasAnyAuthority( "STUDENT","TRAINER")
                        .requestMatchers("/api/student/**").hasAnyAuthority( "STUDENT")
                        .requestMatchers("/api/trainer/**").hasAuthority("TRAINER")
                        .requestMatchers("/user/**").hasAnyAuthority("USER")
                        .anyRequest().authenticated())
                .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider()).addFilterBefore(
                        jwtAuthFIlter, UsernamePasswordAuthenticationFilter.class
                );
        return httpSecurity.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(ourUserDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
