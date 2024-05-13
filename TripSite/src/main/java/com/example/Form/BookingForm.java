package com.example.Form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class BookingForm {
	
	private Integer bookingid;
	
	@NotNull
	private Integer adult;
	
	@NotNull
	private Integer child;
	
	@NotBlank
	private String startDay;
	
	@NotBlank
	private String finishDay;
	
	private String customer;
	private String status;
	
	private int[] low;
	private int[] normal;
	private int[] high;
	
}