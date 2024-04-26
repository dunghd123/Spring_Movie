package ra.spring_movie.config;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import ra.spring_movie.enums.RoleEnums;
import ra.spring_movie.jwt.JwtAuthenticationFilter;
import ra.spring_movie.services.impl.UserDetailServiceImpl;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final UserDetailServiceImpl userDetailService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth-> auth
                        .requestMatchers("/authentication/**").permitAll()
                        .requestMatchers("/sortmoviebyticket/**").permitAll()
                        .requestMatchers("/api/v1/schedule/**").hasAnyAuthority(RoleEnums.ADMIN.name())
                        .requestMatchers("/api/v1/food/**").hasAnyAuthority(RoleEnums.ADMIN.name())
                        .requestMatchers("/api/v1/cinema/**").hasAnyAuthority(RoleEnums.ADMIN.name())
                        .requestMatchers("/api/v1/movie/**").hasAnyAuthority(RoleEnums.ADMIN.name())
                        .requestMatchers("/api/v1/room/**").hasAnyAuthority(RoleEnums.ADMIN.name())
                        .requestMatchers("/api/v1/seat/**").hasAnyAuthority(RoleEnums.ADMIN.name())
                        .anyRequest().authenticated())
                .userDetailsService(userDetailService)
                .sessionManagement(session-> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider= new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        return daoAuthenticationProvider;
    }
    @Bean
    public UserDetailsService userDetailsService(){
        return new UserDetailServiceImpl();
    }
}
