package themion7.my_chat.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import themion7.my_chat.backend.security.jwt.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
       http
            .csrf()
                .disable()
            .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
            .addFilter(new JwtAuthenticationFilter(authenticationManager()))
            .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/room")
                    .permitAll()
                .antMatchers(HttpMethod.POST, "/room/**")
                    .permitAll()
                .antMatchers(HttpMethod.POST, "/member")
                    .permitAll()
                .antMatchers(HttpMethod.DELETE, "/member/**")
                    .authenticated()
        ;

    }

}
