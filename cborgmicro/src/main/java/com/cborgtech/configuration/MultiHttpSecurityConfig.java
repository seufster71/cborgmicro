package com.cborgtech.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.toasthub.security.filter.ToasthubLoginFilter;
import org.toasthub.security.service.UserManagerSvc;

import com.cborgtech.controller.RestAuthenticationEntryPoint;


@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class MultiHttpSecurityConfig {
	
	@Autowired
	UserManagerSvc userManagerSvc;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
	
	@Configuration
	@Order(1)                                                        
	public class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
		
		@Autowired
		private RestAuthenticationEntryPoint restAuthenticationEntryPoint;
		
		protected void configure(HttpSecurity http) throws Exception {
			http.csrf().disable()
				.antMatcher("/api/**")
				.addFilterAfter(toasthubLoginFilter(), BasicAuthenticationFilter.class)
				.exceptionHandling()
					.authenticationEntryPoint(restAuthenticationEntryPoint)
				.and()
				.authorizeRequests()
					.antMatchers("/api/public/**","/api/login/**").permitAll()
					.anyRequest().authenticated();
		}
	}

	@Configuration                                                   
	public class FormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

		@Autowired
	    private AccessDeniedHandler accessDeniedHandler;
		
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.csrf().disable()
            .authorizeRequests()
				.antMatchers("/", "/login/**", "/1.0/**", "/css/**", "/img/**", "/libs/**", "/js/**", "/public/**").permitAll()
				.antMatchers("/test/**").hasAuthority("PRIVATE")
				.anyRequest().authenticated()
            .and()
            .formLogin()
				.loginPage("/login/login.html")
				.permitAll()
				.and()
            .logout()
            	.logoutUrl("/logout.html")
            	.invalidateHttpSession(true)
				.and()
			.exceptionHandling()
				.accessDeniedHandler(accessDeniedHandler);
		}
	}
	
	private ToasthubLoginFilter toasthubLoginFilter() {
		return new ToasthubLoginFilter(userManagerSvc);
	}
}
