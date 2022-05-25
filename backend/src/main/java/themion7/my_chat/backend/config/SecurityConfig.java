package themion7.my_chat.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
       http
            .csrf()
                .disable()
            .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/room")
                    .permitAll()
                .antMatchers(HttpMethod.POST, "/room")
                    .permitAll()
                .antMatchers(HttpMethod.POST, "/member")
                    .permitAll()
                .antMatchers(HttpMethod.DELETE, "/member/**")
                    .authenticated()
        ;

    }

}
