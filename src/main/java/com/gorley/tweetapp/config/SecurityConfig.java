package com.gorley.tweetapp.config;

import com.gorley.tweetapp.service.TweetAppUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration //to declare beans
@EnableWebSecurity //to enable web security
public class SecurityConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer { //extend to override and change implemenatiton as per our our need

    @Autowired
    private TweetAppUserDetailsService tweetAppUserDetailsService;

    @Autowired
    private JwtAuthFilter jwtFilter;

    @Autowired
    private JwtAuthenricationEntryPoint jwtAuthenricationEntryPoint;

    //If you want to continue with boot starter packages, according to release notes you need to override authanticationManagerBean method inside the WebSecurityConfigurerAdapter . Here code sample
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception { //to define which url to permite and whch to block
        http.csrf().disable() // Cross site request forgery is an attach that forces end user to execute unwanted actions on a webappliocation in which they are currently authenticated
                .authorizeRequests().antMatchers("/login","/register", "/forgotpassword","/ping",
                "/v2/api-docs/**",
                "/swagger-ui/**",
                "/swagger-ui.html",
                "/swagger-resources/**",
                "/webjars/**").permitAll() //permitting site with auth
                .anyRequest().authenticated()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().exceptionHandling().authenticationEntryPoint(jwtAuthenricationEntryPoint)
                .and().cors();
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class); //first time once per request - will check if validated
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception { //which auth to use eg-userdetailsservice
        auth.userDetailsService(tweetAppUserDetailsService);//need to create a new userdetails service and extend the new userdetailsservice with spring userdetails service
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowedOrigins("*");
        WebMvcConfigurer.super.addCorsMappings(registry);
    }

    @Bean
    public PasswordEncoder passwordEncorder() {
        return NoOpPasswordEncoder.getInstance();//encode the password
    }
}
