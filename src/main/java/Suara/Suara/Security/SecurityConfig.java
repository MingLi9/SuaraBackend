package Suara.Suara.Security;

import Suara.Suara.Filter.CustomAuthenticationFilter;
import Suara.Suara.Filter.CustomAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.hibernate.sql.Delete;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletResponse;

import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;


/**
 * @author Ming Janssen
 * @version 1.0
 * @since 29/11/2021
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        String refreshTokenEndPoint = "/refresh/**";
        String songEndPoint = "/song/**";
        String playlistEndPoint = "/playlist/**";
        String albumEndPoint = "/album/**";
        String user = "User";
        String creator = "Creator";
        String admin = "Admin";
        http.csrf().disable().cors();
        http.sessionManagement().sessionCreationPolicy(STATELESS);
        http.exceptionHandling().authenticationEntryPoint((req, rsp, e) -> rsp.sendError(SC_UNAUTHORIZED));
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/login").permitAll();
        http.authorizeRequests().antMatchers(GET, refreshTokenEndPoint).permitAll();
        http.authorizeRequests().antMatchers(GET, songEndPoint).hasAuthority(user);
        http.authorizeRequests().antMatchers(PUT, songEndPoint).hasAuthority(creator);
        http.authorizeRequests().antMatchers(POST, songEndPoint).hasAuthority(creator);
        http.authorizeRequests().antMatchers(DELETE,  songEndPoint).hasAuthority(admin);
        http.authorizeRequests().antMatchers(GET,  albumEndPoint).hasAuthority(user);
        http.authorizeRequests().antMatchers(POST,  albumEndPoint).hasAuthority(creator);
        http.authorizeRequests().antMatchers(PUT,  albumEndPoint).hasAuthority(creator);
        http.authorizeRequests().antMatchers(DELETE,  albumEndPoint).hasAuthority(creator);
        http.authorizeRequests().antMatchers(playlistEndPoint).hasAuthority(user);
        http.authorizeRequests().antMatchers(PUT, playlistEndPoint).hasAuthority(admin);

        http.authorizeRequests().anyRequest().permitAll();
        http.addFilter(new CustomAuthenticationFilter(authenticationManagerBean()));
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    //CORS fix: https://www.codegrepper.com/code-examples/java/HttpSecurity+cors+spring+boot
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:3000")
                        .allowedMethods("POST", "GET", "PUT", "DELETE")
                        .allowCredentials(true);
            }
        };
    }
}
