package com.softserve.lv_427.travel_agency.service.impl;

import com.softserve.lv_427.travel_agency.dao.CityDao;
import com.softserve.lv_427.travel_agency.dto.CityDto;
import com.softserve.lv_427.travel_agency.entity.City;
import com.softserve.lv_427.travel_agency.entity.Hotel;
import com.softserve.lv_427.travel_agency.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service implementation for City using CityDao.
 *
 * @author Nazar Vladyka
 * @version 1.0
 */
@Service
public class CityServiceImpl implements CityService {
  private final CityDao dao;

  @Autowired
  public CityServiceImpl(CityDao dao) {
    this.dao = dao;
  }

  /**
   * Add City to DB.
   *
   * @param city City entity.
   */
  @Override
  @Transactional
  public void add(City city) {
    dao.add(city);
  }

  /**
   * Get City from DB by id.
   *
   * @param id id of City.
   * @return city entity of city.
   */
  @Override
  @Transactional
  public City getById(int id) {
    return dao.getById(id);
  }

  /**
   * Delete City from DB.
   *
   * @param city entity of City.
   */
  @Override
  @Transactional
  public void delete(City city) {
    dao.delete(city);
  }

  /**
   * Edit City in DB.
   *
   * @param city entity of City.
   */
  @Override
  @Transactional
  public void edit(City city) {
    dao.edit(city);
  }

  /**
   * Find all cities in DB.
   *
   * @return List of City, all cities from DB.
   */
  @Override
  @Transactional
  public List<City> findAll() {
    return dao.findAll();
  }

  /**
   * Find all cities with available hotels on dates in country.
   *
   * @param countryId country id.
   * @param startDate start date of searching.
   * @param endDate end date of searching.
   * @return List of cities.
   */
  @Override
  @Transactional
  public List<City> getCitiesWithAvailableHotels(int countryId, String startDate, String endDate) {
    return dao.getCitiesWithAvailableHotels(countryId, startDate, endDate);
  }

  /**
   * Find all hotels in city.
   *
   * @param cityId city id.
   * @return List of hotels.
   */
  @Override
  @Transactional
  public List<Hotel> getHotels(int cityId) {
    return dao.getHotels(cityId);
  }

  /**
   * Get CityDto for response.
   *
   * @param cityId city id.
   * @return CityDto.
   */
  @Override
  @Transactional
  public CityDto getCityDto(int cityId) {
    City city = getById(cityId);

    return new CityDto(city.getName(), city.getCountry(), getHotels(cityId));
  }

  //  @Override
  //  @Transactional
  //  public int getId(String cityName) {
  //    return dao.getId(cityName);
  //  }
}