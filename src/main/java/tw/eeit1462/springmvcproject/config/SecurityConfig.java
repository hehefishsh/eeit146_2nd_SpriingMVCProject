package tw.eeit1462.springmvcproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import tw.eeit1462.springmvcproject.repository.EmployeeRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    http
	        .authorizeHttpRequests(auth -> auth
	            .requestMatchers("/login", "/css/**", "/js/**").permitAll() // 允許所有人訪問登入頁面和靜態資源
	            .requestMatchers("/employeemanagement", "/guideline", "/meetingroom", "/bulletin").authenticated() // 需要登入才能訪問的頁面
	            .anyRequest().authenticated() // 其他所有請求需要認證
	        )
	        .formLogin(form -> form
	            .loginPage("/login") // GET 顯示自定義登入頁面
	            .loginProcessingUrl("/perform_login")
	            .failureUrl("/login?error=true")
	            .defaultSuccessUrl("/clock", true)
	            .permitAll()
	        )
	        .logout(logout -> logout
	            .logoutSuccessUrl("/login")
	            .permitAll()
	        )
	        .csrf(csrf -> csrf.disable()); 

	    return http.build();
	}

    @Bean
    public UserDetailsService userDetailsService(EmployeeRepository employeeRepository) {
        return username -> employeeRepository.findByEmployeeName(username)
            .orElseThrow(() -> new UsernameNotFoundException("用戶未找到: " + username));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); 
    }
}
