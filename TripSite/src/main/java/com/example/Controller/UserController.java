package com.example.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.Form.BookingForm;
import com.example.Form.PlaceSearchForm;
import com.example.Form.UserAddForm;

@RequestMapping("/User")
@Controller
public class UserController {
	
	@GetMapping("/PlaceAll")
	public String placeAll(Model model) {
		model.addAttribute("placeSearchForm", new PlaceSearchForm());
		return "User/PlaceAll";
	}
	
	@GetMapping("/UserAdd")
	public String userAdd(Model model) {
		model.addAttribute("userAddForm",new UserAddForm());
		return "User/UserAdd";
	}
	
	@GetMapping("/Login")
	public String getLogin() {
		return "User/Login";
	}
	
	@PostMapping("/Login")
	public String postLogin() {
		return "redirect:/User/PlaceAll";
	}
	
	@GetMapping("/Booking")
	public String booking(Model model){
		model.addAttribute("bookingForm",new BookingForm());
		return "User/Booking";
	}
}
