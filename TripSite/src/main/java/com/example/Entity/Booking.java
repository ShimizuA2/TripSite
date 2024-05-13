package com.example.Entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class Booking {
	
	private Integer bookingid;
	private String name;
	private Integer adult;
	private Integer child;
	
	@DateTimeFormat(pattern="yyyy-mm-dd")
	private Date startDay;
	
	@DateTimeFormat(pattern="yyyy-mm-dd")
	private Date finishDay;
	
	private String customer;
	private String status;
}
