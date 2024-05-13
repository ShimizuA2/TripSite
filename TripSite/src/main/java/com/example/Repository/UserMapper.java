package com.example.Repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.Entity.MUser;
import com.example.Entity.Place;
import com.example.Entity.Priority;
import com.example.Form.BookingForm;
import com.example.Form.UserAddForm;

@Mapper
public interface UserMapper {
	
	List<Place> placeAll();
	
	void userAdd(UserAddForm user);
	
	MUser getLoginUser(String mail);

	void favoriteAdd(String userid, Integer placeid);

	void favoriteDelete(String userid, Integer placeid);

	MUser loginInfo(String userid);

	List<Place> loginPlaceAll(String userid);

	List<Place> favoriteAll(String userid);

	void bookingAdd(String userid,BookingForm data);

	void bookingPlaceAdd(@Param("placeList")List<Priority> places, @Param("userid")String userid, @Param("data")BookingForm data);

	List<Place> searchByPT(@Param("userid")String userid, @Param("prefectures")String[] prefectures, @Param("tags")String[] tags);

	List<Place> searchByT(@Param("userid")String userid, @Param("tags")String[] tags);

	List<Place> searchByP(@Param("userid")String userid, @Param("prefectures")String[] prefectures);

}