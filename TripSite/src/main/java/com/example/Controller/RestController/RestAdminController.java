package com.example.Controller.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Entity.Booking;
import com.example.Entity.MUser;
import com.example.Entity.Tag;
import com.example.Form.BookingDisplayForm;
import com.example.Form.PlaceDisplayForm;
import com.example.Form.UserAddForm;
import com.example.Service.AdminService;

@RequestMapping("/restAdmin")
@RestController
public class RestAdminController {
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private MessageSource messageSource;
	
	@GetMapping("/PlaceAll")
	public List<PlaceDisplayForm> placeAll() {
		List<PlaceDisplayForm> places = adminService.placeAll();
		return places;
	}
	
	@GetMapping("/BookingAll")
	public List<Booking> bookingAll() {
		return adminService.bookingAll();
	}
	
	@GetMapping("/GetCustomers")
	public List<MUser> getCustomers() {
		return adminService.getCustomers();
	}
	
	@PostMapping("/UpdateBooking")
	public void updateBooking(BookingDisplayForm data) {
		adminService.updateBooking(data);
	}
	
	@PostMapping("/CustomerAdd")
	public RestResult customerAdd(@Validated UserAddForm user,BindingResult bindingResult,Locale locale) {
		if(bindingResult.hasErrors()) {
			Map<String,String> errors = new HashMap<>();
			
			for(FieldError error:bindingResult.getFieldErrors()) {
				String message = messageSource.getMessage(error, locale);
				errors.put(error.getField(), message);
			}
			
			return new RestResult(90,errors);
		}
		adminService.userAdd(user);
		return new RestResult(0,null);
	}
	
	@GetMapping("/UserAll")
	public List<MUser> userAll() {
		return adminService.userAll();
	}
	
	@PostMapping("/UserUpdate")
	public RestResult userUpdate(@Validated UserAddForm user,BindingResult bindingResult,Locale locale) {
		if(bindingResult.hasErrors()) {
			Map<String,String> errors = new HashMap<>();
			
			for(FieldError error:bindingResult.getFieldErrors()) {
				String message = messageSource.getMessage(error, locale);
				errors.put(error.getField(), message);
			}
			return new RestResult(90,errors);
		}
		adminService.userUpdate(user);
		return new RestResult(0,null);
	}
	
	@PostMapping("/UserDelete")
	public void userDelete(UserAddForm user) {
		adminService.userDelete(user.getUserid());
	}
	
	@PostMapping("/TagAdd")
	public void tagAdd(String tag) {
		adminService.tagAdd(tag);
	}
	
	@GetMapping("/DefaultTags")
	public List<Tag> defaultTags() {
		return adminService.defaultTags();
	}
}