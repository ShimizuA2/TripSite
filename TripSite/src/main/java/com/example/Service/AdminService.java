package com.example.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.Entity.Booking;
import com.example.Entity.MUser;
import com.example.Entity.Place;
import com.example.Entity.Tag;
import com.example.Form.BookingDisplayForm;
import com.example.Form.PlaceAddForm;
import com.example.Form.PlaceDisplayForm;
import com.example.Form.UserAddForm;
import com.example.Repository.AdminMapper;

@Service
public class AdminService {
	
	@Autowired
	private AdminMapper adminMapper;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private PasswordEncoder encoder;
	
	public List<PlaceDisplayForm> placeAll(){
		List<Place> placeListByte = adminMapper.placeAll();
		List<PlaceDisplayForm> placeList = new ArrayList<PlaceDisplayForm>();
		for(Place placeByte : placeListByte) {
			PlaceDisplayForm place = commonService.byteToBase64(placeByte);
			placeList.add(place);
		}
		return placeList;
	}
	
	public void placeAdd(Place place, String[] prefectures, String[] tags) {
		adminMapper.placeAdd(place);
		adminMapper.placePrefecture(place,prefectures);
		adminMapper.placeTagAdd(place,tags);
	}
	
	public Place setPlace(PlaceAddForm placeAddForm) {
		Place place = new Place();
		place.setName(placeAddForm.getName());
		place.setAddress(placeAddForm.getAddress());
		place.setDetail(placeAddForm.getDetail());
		byte[] pictureByte;
		try {
			pictureByte = placeAddForm.getPicture().getBytes();
			place.setPicture(pictureByte);
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return place;
	}

	public List<Booking> bookingAll() {
		return adminMapper.bookingAll();
	}

	public BookingDisplayForm bookingDetail(Integer bookingid) {
		Booking booking = adminMapper.bookingDetail(bookingid);
		List<Place> low = adminMapper.bookingPlaceList(bookingid,"low");
		List<Place> normal = adminMapper.bookingPlaceList(bookingid,"normal");
		List<Place> high = adminMapper.bookingPlaceList(bookingid,"high");
		BookingDisplayForm bookingDetail = commonService.intoBookingDisplayForm(booking,low,normal,high);
		return bookingDetail;
	}

	public List<MUser> getCustomers() {
		return adminMapper.getCustomers();
	}

	public void updateBooking(BookingDisplayForm data) {
		adminMapper.updateBooking(data);		
	}

	public void userAdd(UserAddForm user) {
		user.setPassword(encoder.encode(user.getPassword()));
		user.setRole(1);
		adminMapper.customerAdd(user);
	}

	public List<MUser> userAll() {
		return adminMapper.userAll();
	}

	public UserAddForm userEdit(Integer userid) {
		MUser user = adminMapper.userEdit(userid);
		UserAddForm formUser = new UserAddForm();
		formUser.setUserid(userid);
		formUser.setName(user.getName());
		formUser.setMail(user.getMail());
		formUser.setPassword(user.getPassword());
		formUser.setRole(user.getRole());
		return formUser;
	}

	public void userUpdate(UserAddForm user) {
		adminMapper.userUpdate(user);
	}

	public void userDelete(int userid) {
		adminMapper.userDelete(userid);
	}

	public void tagAdd(String tag) {
		Tag newTag = new Tag();
		newTag.setPlaceid(0);
		newTag.setTag(tag);
		adminMapper.tagAdd(newTag);
	}

	public List<Tag> defaultTags() {
		return adminMapper.defaultTags();
	}
}