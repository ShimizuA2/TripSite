package com.example.Service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;

import com.example.Entity.Booking;
import com.example.Entity.Place;
import com.example.Form.BookingDisplayForm;
import com.example.Form.PlaceDisplayForm;

@Service
public class CommonService {
	
	public PlaceDisplayForm byteToBase64(Place placeByte) {
		PlaceDisplayForm place = new PlaceDisplayForm();
		place.setPlaceid(placeByte.getPlaceid());
		place.setName(placeByte.getName());
		place.setAddress(placeByte.getAddress());
		place.setDetail(placeByte.getDetail());
		if(placeByte.getFavoriteid() != null) {
			place.setFavorite("yes");
		}
		
		StringBuffer data  = new StringBuffer();
		String base64;
		try {
			base64 = new String(Base64.encodeBase64(placeByte.getPicture()),"ASCII");
			data.append("data:image/jpeg;base64,");
			data.append(base64);
		} catch (UnsupportedEncodingException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		place.setPicture(data.toString());
		return place;
	}

	public BookingDisplayForm intoBookingDisplayForm(Booking booking, List<Place> low, List<Place> normal, List<Place> high) {
		BookingDisplayForm form = new BookingDisplayForm();
		form.setBookingid(booking.getBookingid());
		form.setName(booking.getName());
		form.setAdult(booking.getAdult());
		form.setChild(booking.getChild());
		form.setStartDay(booking.getStartDay());
		form.setFinishDay(booking.getFinishDay());
		form.setCustomer(booking.getCustomer());
		form.setStatus(booking.getStatus());
		
		if(low != null) {
			List<PlaceDisplayForm> list = new ArrayList<PlaceDisplayForm>();
			for(Place placeByte : low) {
				list.add(byteToBase64(placeByte));
			}
			form.setLow(list);
		}
		
		if(normal != null) {
			List<PlaceDisplayForm> list = new ArrayList<PlaceDisplayForm>();
			for(Place placeByte : normal) {
				list.add(byteToBase64(placeByte));
			}
			form.setNormal(list);
		}
		
		if(high != null) {
			List<PlaceDisplayForm> list = new ArrayList<PlaceDisplayForm>();
			for(Place placeByte : high) {
				list.add(byteToBase64(placeByte));
			}
			form.setHigh(list);
		}
		
		return form;
	}
}