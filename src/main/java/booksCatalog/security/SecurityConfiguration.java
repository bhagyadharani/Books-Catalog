package booksCatalog.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
// 		Needs authentication 
		http.authorizeHttpRequests().anyRequest().authenticated();
     	 
		http.httpBasic();
		http.csrf().disable(); // needed for POST requests
		http.cors();

		return http.build();
	}
}