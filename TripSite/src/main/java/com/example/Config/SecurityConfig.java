package com.example.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
    @Override
	public void configure(WebSecurity web) throws Exception{
        web
        	.ignoring()
        		.antMatchers("/webjars/**")
        		.antMatchers("/js/**")
        		.antMatchers("/h2-console/**")
        		.antMatchers("/css/**");
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //リンクの設定
    	http
    		.authorizeRequests()
    			.antMatchers("/User/Login").permitAll()
    			.antMatchers("/User/UserAdd").permitAll()
    			.antMatchers("/User/PlaceAll").permitAll()
    			.antMatchers("/restUser/UserAdd").permitAll()
    			.antMatchers("/restUser/PlaceAll").permitAll()
    			.antMatchers("/restUser/LoginInfo").permitAll()
    			.antMatchers("/restAdmin/DefaultTags").permitAll()
    			.antMatchers("/restUser/PlaceSearch").permitAll()
    			.antMatchers("/Admin/*").hasAuthority("ROLE_ADMIN")
    			.anyRequest().authenticated();
        
        //ログイン処理
        http
        	.formLogin()
        		.loginProcessingUrl("/User/Login")
        		.loginPage("/User/Login")
        		.failureUrl("/User/Login?error")
        		.usernameParameter("mail")
        		.passwordParameter("password")
        		.defaultSuccessUrl("/User/PlaceAll",true);
        
        http
        	.logout()
        		.logoutUrl("/logout")
        		.logoutSuccessUrl("/User/PlaceAll");
        
        http.csrf().disable();
	}
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    	
    	PasswordEncoder encoder =passwordEncoder();
    	
    	auth
    		.userDetailsService(userDetailsService)
    		.passwordEncoder(encoder);
    }

}
