package com.leo.test.xstream;

import java.util.List;

import com.thoughtworks.xstream.XStream;

public class TestXstream {
	public static XStream xstream = new XStream();

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		xstream.alias("hotelinfo", HotelInfo.class);
        xstream.alias("room",RoomInfo.class);
        xstream.addImplicitCollection(HotelInfo.class, "room",RoomInfo.class);
        xstream.useAttributeFor(String.class);
        xstream.useAttributeFor(Integer.class);
		List<HotelInfo> hotels = (List<HotelInfo>) xstream.fromXML(TestXstream.class.getClassLoader()
				.getResourceAsStream("hotel.xml"));
		for (int i = 0; i < hotels.size(); i++) {
			System.out.println(hotels.get(i));
		}
//		HotelInfo hotel=(HotelInfo) xstream.fromXML(TestXstream.class.getClassLoader()
//				.getResourceAsStzream("hotel.xml"));
//		System.out.println(hotel);
	}
	
}
