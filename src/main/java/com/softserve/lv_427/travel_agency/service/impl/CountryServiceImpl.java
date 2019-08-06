package com.softserve.lv_427.travel_agency.service.impl;

import com.softserve.lv_427.travel_agency.dao.CountryDao;
import com.softserve.lv_427.travel_agency.dto.CountryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.softserve.lv_427.travel_agency.dao.impl.CountryDaoImpl;
import com.softserve.lv_427.travel_agency.entity.City;
import com.softserve.lv_427.travel_agency.entity.Country;
import com.softserve.lv_427.travel_agency.service.external.AutoPopulateDB;
import com.softserve.lv_427.travel_agency.service.CountryService;

import java.util.List;

/**
 * Service for Country using CountryDao.
 *
 * @author Nazar Vladyka
 * @version 1.0
 */
@Service
public class CountryServiceImpl implements CountryService {
  private final CountryDao dao;

  @Autowired
  public CountryServiceImpl(CountryDao dao) {
    this.dao = dao;
  }

  /**
   * Add country to DB.
   *
   * @param country Country entity.
   */
  @Override
  public void add(Country country) {
    dao.add(country);
  }

  /**
   * Get country from DB by id.
   *
   * @param id country id.
   * @return Country entity.
   */
  @Override
  public Country getById(int id) {
    return dao.getById(id);
  }

  /**
   * Delete country from DB.
   *
   * @param country country entity.
   */
  @Override
  public void delete(Country country) {
    dao.delete(country);
  }

  /**
   * Edit country in DB.
   *
   * @param country country entity.
   */
  @Override
  public void edit(Country country) {
    dao.edit(country);
  }

  /**
   * Get all countries from DB.
   *
   * @return List of Countries.
   */
  @Override
  public List<Country> findAll() {
    return dao.findAll();
  }

  /**
   * Get countries visited by client.
   *
   * @param clientId client id.
   * @return List of Countries
   */
  @Override
  public List<Country> getVisitedCountries(int clientId) {
    return dao.getVisitedCountries(clientId);
  }

  /**
   * Get all cities in the country.
   *
   * @param id country id.
   * @return List of Cities.
   */
  @Override
  public List<City> getCitiesByCountryId(int id) {
    return dao.getCitiesByCountryId(id);
  }

  /**
   * Get CountryDto by country id.
   *
   * @param countryId country id.
   * @return CountryDto for the controller.
   */
  @Override
  public CountryDto getCountryDto(int countryId) {
    Country country = getById(countryId);

    return new CountryDto(country.getName(), getCitiesByCountryId(countryId));
  }
}
