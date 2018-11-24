package rjm.romek.awscourse;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);

    @Value("${credentials.path}")
    private String credentialsPath;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/home").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/chapter/1", true)
                .permitAll()
                .and()
                .logout()
                .permitAll();
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager(loadUsers());
    }

    private UserDetails [] loadUsers() {
        List<String> credentials = null;
        List<UserDetails> userDetails = new ArrayList<UserDetails>();

        try {
            File file = new ClassPathResource(credentialsPath).getFile();
            credentials = FileUtils.readLines(file, "UTF8");
            for (String userData : credentials) {
                String [] userDataSplited = userData.split(",");
                String userName = userDataSplited[0];
                String password = userDataSplited[1];
                String authority = userDataSplited[2];
                userDetails.add(
                        User.withDefaultPasswordEncoder()
                                .username(userName)
                                .password(password)
                                .authorities(authority)
                                .build()
                );
            }
        } catch (IOException e) {
            logger.error("Could not read users. Exception: " + e.getMessage());
            throw new RuntimeException(e);
        }

        return userDetails.toArray(new UserDetails[userDetails.size()]);
    }
}
