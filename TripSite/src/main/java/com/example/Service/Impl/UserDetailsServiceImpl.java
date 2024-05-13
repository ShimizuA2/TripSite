package com.example.Service.Impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.Entity.MUser;
import com.example.Service.UserService;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	
	@Autowired
	private UserService userService;
	
	@Override
	public UserDetails loadUserByUsername(String mail)
		throws UsernameNotFoundException {
			
			//ユーザ情報取得
			MUser loginUser = userService.getLoginUser(mail);
			
			if(loginUser==null) {
				throw new UsernameNotFoundException("user not found");
			}
			
			GrantedAuthority authority;
			List<GrantedAuthority> authorities = new ArrayList<>();
			
			if(loginUser.getRole() == 1) {
				authority = new SimpleGrantedAuthority("ROLE_ADMIN");
				authorities.add(authority);
			} else if(loginUser.getRole() == 2) {
				authority = new SimpleGrantedAuthority("ROLE_GENERAL");
				authorities.add(authority);
			}
			
			
			UserDetails userDetails = (UserDetails) new User(loginUser.getUserid(),loginUser.getPassword(),authorities);
			
			return userDetails;
	}

}