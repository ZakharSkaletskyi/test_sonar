package com.softserve.lv_427.travel_agency.service.impl;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.softserve.lv_427.travel_agency.dao.ClientDao;
import com.softserve.lv_427.travel_agency.dto.CityDto;
import com.softserve.lv_427.travel_agency.dto.ClientPeriodDto;
import com.softserve.lv_427.travel_agency.entity.Client;
import com.softserve.lv_427.travel_agency.entity.Country;
import com.softserve.lv_427.travel_agency.entity.Hotel;
import com.softserve.lv_427.travel_agency.service.ClientService;

@Service
public class ClientServiceImpl implements ClientService {
  private ClientDao dao;

  @Autowired
  public ClientServiceImpl(ClientDao dao) {
    this.dao = dao;
  }

  @Override
  @Transactional
  public Client getById(int id) {
    return dao.getById(id);
  }

  @Override
  @Transactional
  public void add(Client client) {
    dao.add(client);
  }

  @Override
  @Transactional
  public void delete(Client client) {
    dao.delete(client);
  }

  @Override
  public void edit(Client client) {
    dao.edit(client);
  }

  @Override
  @Transactional
  public List<Client> getAllClients() {
    return dao.getAllClient();
  }

  @Override
  @Transactional
  public int getClientId(String firstName, String lastName)
      throws SQLException, ClassNotFoundException {
    return dao.getClientId(firstName, lastName);
  }

  @Override
  @Transactional
  public Client getClient(int id) throws SQLException, ClassNotFoundException {
    return dao.getClient(id);
  }

  @Override
  @Transactional
  public List<Country> getAvailableCountries(int clientId) throws ClassNotFoundException {
    return dao.getAvailableCountries(clientId);
  }

//  @Override
//  @Transactional
//  public ClientPeriodDto getClientPeriodDto(
//      int clientId,
//      @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateStart,
//      @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateEnd) {
//	  return  new ClientPeriodDto(getById(clientId),dateStart,dateEnd);
//  }
  @Override
  @Transactional
  public ClientPeriodDto getClientPeriodDto(
      int clientId,
      String dateStart,
      String dateEnd) {
	  return  new ClientPeriodDto(getById(clientId),dateStart,dateEnd);
  }

@Override public CityDto getCityDto(int cityId){// TODO Auto-generated method stub
return null;}
}
