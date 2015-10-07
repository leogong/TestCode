package com.leo.test.xstream;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 *
 * @Author Leo
 */
public class HotelInfo {
    private int id;

    private String cityUrl;

    private String cityName;

    private String hotelName;

    private String hotelAddress;

    private String hotelTel;

    private Date directionsUpdateTime;

    private String directTime;

    private Date lastUpdateAfterCheck;

    private String seq;

    private List<RoomInfo> room;

    public List<RoomInfo> getRoom() {
        return room;
    }

    public void setRoom(List<RoomInfo> room) {
        this.room = room;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCityUrl() {
        return cityUrl;
    }

    public void setCityUrl(String cityUrl) {
        this.cityUrl = cityUrl;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getHotelAddress() {
        return hotelAddress;
    }

    public void setHotelAddress(String hotelAddress) {
        this.hotelAddress = hotelAddress;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getHotelTel() {
        return hotelTel;
    }

    public void setHotelTel(String hotelTel) {
        this.hotelTel = hotelTel;
    }

    public Date getDirectionsUpdateTime() {
        return directionsUpdateTime;
    }

    public void setDirectionsUpdateTime(Date directionsUpdateTime) {
        this.directionsUpdateTime = directionsUpdateTime;
    }

    public String getDirectTime() {
        return directTime;
    }

    public void setDirectTime(String directTime) {
        this.directTime = directTime;
    }

    public Date getLastUpdateAfterCheck() {
        return lastUpdateAfterCheck;
    }

    public void setLastUpdateAfterCheck(Date lastUpdateAfterCheck) {
        this.lastUpdateAfterCheck = lastUpdateAfterCheck;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SIMPLE_STYLE);
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }
}
