package com.example.Form;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class BookingDisplayForm {
	
	private Integer bookingid;
	private String name;
	private Integer adult;
	private Integer child;
	
	
	private Date startDay;
	private Date finishDay;
	
	private String customer;
	private String status;
	
	private List<PlaceDisplayForm> low;
	private List<PlaceDisplayForm> normal;
	private List<PlaceDisplayForm> high;
}
