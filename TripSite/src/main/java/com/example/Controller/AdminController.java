package com.example.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.Entity.Place;
import com.example.Form.PlaceAddForm;
import com.example.Form.UserAddForm;
import com.example.Service.AdminService;

@RequestMapping("/Admin")
@Controller
public class AdminController {
	
	@Autowired
	private AdminService adminService;
	
	@GetMapping("/Menu")
	public String menu() {
		return "Admin/Menu";
	}
	
	@GetMapping("/PlaceAll")
	public String placeAll() {
		return "Admin/PlaceAll";
	}
	
	@GetMapping("/PlaceAdd")
	public String placeAdd(Model model ,@ModelAttribute PlaceAddForm placeAddForm) {
		return "Admin/PlaceAdd";
	}
	
	@PostMapping("/PlaceAdd")
	public String placeAdd(Model model,@ModelAttribute @Validated PlaceAddForm placeAddForm,BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) {
			return placeAdd(model, placeAddForm);
		}
		
		Place place = adminService.setPlace(placeAddForm);
		adminService.placeAdd(place,placeAddForm.getPrefectures(),placeAddForm.getTags());
		return "Admin/PlaceAll";
	}
	
	@GetMapping("/BookingAll")
	public String bookingAll() {
		return "Admin/BookingAll";
	}
	
	@GetMapping("/BookingDetail/{bookingid}")
	public String bookingDetail(@PathVariable Integer bookingid,Model model) {
		model.addAttribute("bookingDisplayForm", adminService.bookingDetail(bookingid));
		return "Admin/BookingDetail";
	}
	
	@GetMapping("/CustomerAdd")
	public String customerAdd(Model model) {
		model.addAttribute("userAddForm", new UserAddForm());
		return "Admin/CustomerAdd";
	}
	
	@GetMapping("/UserAll")
	public String userAll() {
		return "Admin/UserAll";
	}
	
	@GetMapping("/UserEdit/{userid}")
	public String userEdit(@PathVariable Integer userid,Model model) {
		model.addAttribute("userAddForm", adminService.userEdit(userid));
		return "Admin/UserEdit";
	}
	
	@GetMapping("/TagAdd")
	public String tagAdd() {
		return "Admin/TagAdd";
	}
	
}