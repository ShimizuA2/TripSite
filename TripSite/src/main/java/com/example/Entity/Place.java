package com.example.Entity;

import lombok.Data;

@Data
public class Place {
	
	private Integer placeid;
	private String name;
	private String address;
	private String detail;
	private byte[] picture;
	private Integer favoriteid;
	
}