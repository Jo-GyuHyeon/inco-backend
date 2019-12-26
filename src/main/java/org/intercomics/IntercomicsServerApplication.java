package org.intercomics;

import javax.servlet.Filter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableResourceServer
@EnableAuthorizationServer
@SpringBootApplication
@MapperScan(value= {"org.intercomics.mapper"})
@PropertySource(value = { "classpath:application.properties",  "classpath:${jdbc.config}","classpath:${jwt.config}","classpath:${notification.config}" })
public class IntercomicsServerApplication {
	
	@Autowired
	ApplicationContext context;
	
	@Bean
	public ResourceServerConfigurerAdapter resourceServerConfigurerAdapter() {
		return new ResourceServerConfigurerAdapter() {
			
			
			@Override
			public void configure(HttpSecurity http) throws Exception {
				http.headers().frameOptions().disable();
				http.authorizeRequests()
				.antMatchers("/test").access("#oauth2.hasScope('read')")
				.anyRequest().permitAll();
				
				http.formLogin()
				.loginPage("/login")
				.usernameParameter("userName")
				.passwordParameter("password")
				.loginProcessingUrl("/loginProcessing")
				.defaultSuccessUrl("/home")
				.failureUrl("/login?error=false");
        
				http.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/");
				
				http.addFilterBefore((Filter)context.getBean("sso.filter"), BasicAuthenticationFilter.class);
				
			}
		};
	}

	
	public static void main(String[] args) {
		SpringApplication.run(IntercomicsServerApplication.class, args);
	}
}
