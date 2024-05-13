package com.example.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.Entity.MUser;
import com.example.Entity.Place;
import com.example.Entity.Priority;
import com.example.Form.BookingForm;
import com.example.Form.PlaceDisplayForm;
import com.example.Form.PlaceSearchForm;
import com.example.Form.UserAddForm;
import com.example.Repository.UserMapper;

@Service
public class UserService {
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private PasswordEncoder encoder;
	
	public List<PlaceDisplayForm> placeAll(String userid){
		List<Place> placeListByte = new ArrayList<Place>();
		if(userid.equals("anonymousUser")) {
			placeListByte = userMapper.placeAll();
		}else {
			placeListByte = userMapper.loginPlaceAll(userid);
		}
		List<PlaceDisplayForm> placeList = new ArrayList<PlaceDisplayForm>();
		for(Place placeByte : placeListByte) {
			PlaceDisplayForm place = commonService.byteToBase64(placeByte);
			placeList.add(place);
		}
		return placeList;
	}
	
	public void userAdd(UserAddForm user) {
		user.setPassword(encoder.encode(user.getPassword()));
		user.setRole(2);
		userMapper.userAdd(user);
	}
	
	public MUser getLoginUser(String mail) {
		return userMapper.getLoginUser(mail);
	}

	public void favoriteAdd(String userid, Integer placeid) {
		userMapper.favoriteAdd(userid,placeid);
	}

	public void favoriteDelete(String userid, Integer placeid) {
		userMapper.favoriteDelete(userid,placeid);		
	}

	public MUser loginInfo(String userid) {
		return userMapper.loginInfo(userid);
	}

	public List<PlaceDisplayForm> favoriteAll(String userid) {
		List<Place> favoriteListByte = userMapper.favoriteAll(userid);
		List<PlaceDisplayForm> favoriteList = new ArrayList<PlaceDisplayForm>();
		for(Place placeByte : favoriteListByte) {
			PlaceDisplayForm place = commonService.byteToBase64(placeByte);
			favoriteList.add(place);
		}
		return favoriteList;
	}

	public void bookingAdd(String userid,BookingForm data) {
		data.setCustomer("未決定");
		data.setStatus("未完了");
		userMapper.bookingAdd(userid,data);
		
		List<Priority> places = new ArrayList<Priority>();
		if(data.getLow().length > 0) {
			places = setPlaces(places,data.getLow(),"low");
		}
		if(data.getNormal().length > 0) {
			places = setPlaces(places,data.getNormal(),"normal");
		}
		if(data.getHigh().length > 0) {
			places = setPlaces(places,data.getHigh(),"high");
		}
		userMapper.bookingPlaceAdd(places,userid,data);
	}
	
	public List<Priority> setPlaces(List<Priority> places, int[] placeidList, String priority) {
		for(int i = 0; i < placeidList.length; i++) {
			Priority place = new Priority();
			place.setPlaceid(placeidList[i]);
			place.setPriority(priority);
			places.add(place);
		}
		return places;
	}

	public List<PlaceDisplayForm> placeSearch(String userid, PlaceSearchForm searchForm) {
		if(userid.equals("anonymousUser")) {
			userid = "0";
		}
		List<Place> resultByte = new ArrayList<Place>();
		if(searchForm.getPrefectures().length > 0 && searchForm.getTags().length > 0) {
			resultByte = userMapper.searchByPT(userid,searchForm.getPrefectures(),searchForm.getTags());
		}else if(searchForm.getPrefectures().length == 0 && searchForm.getTags().length > 0) {
			resultByte = userMapper.searchByT(userid,searchForm.getTags());
		}else if(searchForm.getPrefectures().length > 0 && searchForm.getTags().length == 0) {
			resultByte = userMapper.searchByP(userid,searchForm.getPrefectures());
		}
		List<PlaceDisplayForm> result = new ArrayList<PlaceDisplayForm>();
		for(Place placeByte : resultByte) {
			result.add(commonService.byteToBase64(placeByte));
		}
		return result;
	}
	
}