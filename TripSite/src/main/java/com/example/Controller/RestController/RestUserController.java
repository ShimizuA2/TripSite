package com.example.Controller.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Entity.MUser;
import com.example.Form.BookingForm;
import com.example.Form.PlaceDisplayForm;
import com.example.Form.PlaceSearchForm;
import com.example.Form.UserAddForm;
import com.example.Service.UserService;

@RequestMapping("/restUser")
@RestController
public class RestUserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MessageSource messageSource;
	
	@GetMapping("LoginInfo")
	public MUser loginInfo() {
		MUser user = new MUser();
		String userid = SecurityContextHolder.getContext().getAuthentication().getName();
		if(userid.equals("anonymousUser")) {
			user = null;
		}else {
			user = userService.loginInfo(userid);
		}
		return user;
	}
	
	@GetMapping("/PlaceAll")
	public List<PlaceDisplayForm> placeAll() {
		String userid = SecurityContextHolder.getContext().getAuthentication().getName();
		return userService.placeAll(userid);
	}
	
	@PostMapping("/UserAdd")
	public RestResult userAdd(@Validated UserAddForm user,BindingResult bindingResult,Locale locale) {
		
		if(bindingResult.hasErrors()) {
			Map<String,String> errors = new HashMap<>();
			
			for(FieldError error:bindingResult.getFieldErrors()) {
				String message = messageSource.getMessage(error, locale);
				errors.put(error.getField(), message);
			}
			
			return new RestResult(90,errors);
		}
		userService.userAdd(user);
		return new RestResult(0,null);
		
	}
	
	@PostMapping("/FavoriteAdd")
	public void favoriteAdd(@RequestBody String data) {
		int placeid = Integer.parseInt(data.replace("placeid=", ""));
		String userid = SecurityContextHolder.getContext().getAuthentication().getName();
		userService.favoriteAdd(userid,placeid);
	}
	
	@PostMapping("/FavoriteDelete")
	public void favoriteDelete(@RequestBody String data) {
		int placeid = Integer.parseInt(data.replace("placeid=", ""));
		String userid = SecurityContextHolder.getContext().getAuthentication().getName();
		userService.favoriteDelete(userid,placeid);
	}
	
	@GetMapping("/FavoriteAll")
	public List<PlaceDisplayForm> favoriteAll() {
		String userid = SecurityContextHolder.getContext().getAuthentication().getName();
		return userService.favoriteAll(userid);
	}
	
	@PostMapping("/BookingAdd")
	public RestResult bookingAdd(@Validated BookingForm data,BindingResult bindingResult,Locale locale) {
		
		if(bindingResult.hasErrors()) {
			Map<String,String> errors = new HashMap<>();
			
			for(FieldError error:bindingResult.getFieldErrors()) {
				String message = messageSource.getMessage(error, locale);
				errors.put(error.getField(), message);
			}
			
			return new RestResult(90,errors);
		}
		String userid = SecurityContextHolder.getContext().getAuthentication().getName();
		userService.bookingAdd(userid,data);
		return new RestResult(0,null);
	}
	
	@GetMapping("/PlaceSearch")
	public List<PlaceDisplayForm> placeSearch(PlaceSearchForm searchForm) {
		String userid = SecurityContextHolder.getContext().getAuthentication().getName();
		return userService.placeSearch(userid,searchForm);
	}
}