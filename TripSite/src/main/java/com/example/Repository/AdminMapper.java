package com.example.Repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.Entity.Booking;
import com.example.Entity.MUser;
import com.example.Entity.Place;
import com.example.Entity.Tag;
import com.example.Form.BookingDisplayForm;
import com.example.Form.UserAddForm;

@Mapper
public interface AdminMapper {
	
	List<Place> placeAll();
	
	void placeAdd(Place place);

	List<Booking> bookingAll();

	Booking bookingDetail(Integer bookingid);

	List<Place> bookingPlaceList(Integer bookingid,String priority);

	List<MUser> getCustomers();

	void updateBooking(BookingDisplayForm data);

	void customerAdd(UserAddForm user);

	List<MUser> userAll();

	MUser userEdit(Integer userid);

	void userUpdate(UserAddForm user);

	void userDelete(int userid);

	void tagAdd(Tag newTag);

	List<Tag> defaultTags();

	void placeTagAdd(@Param("place")Place place, @Param("tags")String[] tags);
	
	void placePrefecture(@Param("place")Place place, @Param("prefectures")String[] prefectures);

}