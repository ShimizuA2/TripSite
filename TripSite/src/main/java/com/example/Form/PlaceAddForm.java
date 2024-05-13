package com.example.Form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import org.springframework.web.multipart.MultipartFile;

import com.example.Interface.FileRequired;

import lombok.Data;

@Data
public class PlaceAddForm {
	
	@NotBlank
	private String name;
	
	@NotBlank
	private String address;
	
	@NotBlank
	private String detail;
	
	@FileRequired
	private MultipartFile picture;
	
	@NotEmpty
	private String[] prefectures;
	
	private String[] tags;
	
}