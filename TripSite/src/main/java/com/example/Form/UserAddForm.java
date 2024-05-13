package com.example.Form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class UserAddForm {
	
	private Integer userid;
	
	@NotBlank
	private String name;
	
	@NotBlank
	@Email
	private String mail;
	
	@NotBlank
	private String password;
	
	private Integer role;
	
}