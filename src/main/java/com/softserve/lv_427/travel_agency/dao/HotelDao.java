package com.softserve.lv_427.travel_agency.dao;

import com.softserve.lv_427.travel_agency.entity.Hotel;

import java.util.List;

public interface HotelDao {
  void add(Hotel hotel);

  Hotel getById(int id);

  void delete(Hotel hotel);

  void edit(Hotel hotel);

  List<Hotel> getAll();

  int getId(String name);

  int getClientCountForPeriod(int hotel_id, String dateStart, String dateEnd);

  List<Hotel> getAvailableHotelsOnDates(String startDate, String endDate);

  int getAverageBookTime(int hotel_id, String dateStart, String dateEnd);

  List<Hotel> getAvailableHotelsOnDatesInCity(int cityId, String startDate, String endDate)
      throws ClassNotFoundException;
}
