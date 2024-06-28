/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hu.anzek.repteri_jegyertekesites.config.security;


import hu.anzek.repteri_jegyertekesites.util.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


/**
 *
 * @author User
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig  {

    @Autowired
    private JwtRequestFilter jwtRequestFilter;
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(csrf -> csrf.disable()) // csak fejlesztés idejére!
                .authorizeHttpRequests(authz -> authz
                .requestMatchers("/web/login",                  // WEB-bongésző
                                 "/web/error",                                            
                                 "/web/home").permitAll()      
                .requestMatchers("/api/login/**",           // RESTful 
                                 "/api/regisztracio/**").permitAll()                        
                .requestMatchers("/resources/**",           // általános rendszer összetevők
                                 "/static/**",                 
                                 "/css/**",                 
                                 "/js/**").permitAll()      // szabad hozzáférést adunk
                .requestMatchers("/**").authenticated()     // authetikáclt hozzáfáérést adunk
                .anyRequest().authenticated()
                )                
                .formLogin(form -> form
                                       .loginPage("/web/login")
                                       .defaultSuccessUrl("/web/home", true)
                                       .failureUrl("/web/error")
                                       .permitAll()
                           )                
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );        
        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}    
