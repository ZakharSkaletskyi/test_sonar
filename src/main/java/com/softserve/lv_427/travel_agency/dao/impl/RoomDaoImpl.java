package com.softserve.lv_427.travel_agency.dao.impl;

import com.softserve.lv_427.travel_agency.dao.RoomDao;
import com.softserve.lv_427.travel_agency.entity.Country;
import com.softserve.lv_427.travel_agency.entity.Hotel;
import com.softserve.lv_427.travel_agency.entity.Room;
import com.softserve.lv_427.travel_agency.exception.FieldNotFoundException;
import com.softserve.lv_427.travel_agency.service.external.DaysService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.xml.crypto.Data;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * DAO implementation for Room entity.
 *
 * @author Oleksandr Agarkov
 * @version 1.0
 */
@Repository
public class RoomDaoImpl implements RoomDao {
  private SessionFactory sessionFactory;
  private DaysService daysService;

  @Autowired
  public void setSessionFactory(SessionFactory sessionFactory, DaysService daysService) {
    this.sessionFactory = sessionFactory;
    this.daysService = daysService;
  }

  /**
   * Add Room entity to DB.
   *
   * @param room - room entity.
   */
  @Override
  public void add(Room room) {
    try (Session session = sessionFactory.openSession()) {
      session.persist(room);
    }
  }

  /**
   * Get room from DB by id.
   *
   * @param id - room id.
   * @return room entity.
   */
  @Override
  public Room getById(int id) {
    try (Session session = sessionFactory.openSession()) {
      Room room = session.get(Room.class, id);
      if (room == null) {
        throw new FieldNotFoundException("There is no room with this name");
      } else {
        return room;
      }
    }
  }

  /**
   * Delete room from DB.
   *
   * @param room - room entity.
   */
  @Override
  public void delete(Room room) {
    try (Session session = sessionFactory.openSession()) {
      session.delete(room);
    }
  }

  /**
   * Edit room in DB.
   *
   * @param room - room entity.
   */
  @Override
  public void edit(Room room) {
    try (Session session = sessionFactory.openSession()) {
      session.update(room);
    }
  }

  /**
   * Get the list of available rooms in certain hotel
   *
   * @param hotelId - hotel id.
   * @param startDate - start date.
   * @param endDate - end date
   * @return list of available rooms.
   */
  @Override
  public List<Room> getAvailableRoomsOnDateInHotel(String startDate, String endDate, int hotelId) {
    try (Session session = sessionFactory.openSession()) {

      List<Room> rooms =
          session
              .createQuery(
                  "SELECT r from Hotel h join h.rooms r left join r.roomBooks rb where "
                      + " (rb.orderStart > ?2 OR rb.orderEnd < ?1"
                      + "OR rb.orderStart is null) AND h.id = ?3",
                  Room.class)
              .setParameter(1, startDate)
              .setParameter(2, endDate)
              .setParameter(3, hotelId)
              .list();
      if (rooms == null) {
        throw new FieldNotFoundException("There is no available rooms");
      } else {
        return rooms;
      }
    }
  }

  /**
   * Get room's loading in days
   *
   * @param roomId - room id.
   * @param startDate - start date.
   * @param endDate - end date
   * @return int - room's loading in days
   */
  @Override
  public int getLoadingRoomsPeriod(String startDate, String endDate, int roomId) {
    try (Session session = sessionFactory.openSession()) {
      List<Object[]> roomsLoading =
          new ArrayList<>(
              session
                  .createQuery(
                      "SELECT orderStart, orderEnd FROM RoomBookArchive WHERE "
                          + "(orderStart >= ?1  AND orderEnd <= ?2 AND room.id IN "
                          + "(SELECT id FROM Room where id = ?3))",
                      Object[].class)
                  .setParameter(1, startDate)
                  .setParameter(2, endDate)
                  .setParameter(3, roomId)
                  .list());
      List<Integer> dateDifference = new ArrayList<>();
      for (Object[] date : roomsLoading) {
        dateDifference.add(daysService.getDaysFromPeriod((String) date[0], (String) date[1]));
      }
      return dateDifference.size() > 0
          ? dateDifference.stream().mapToInt(Integer::intValue).sum()
          : 0;
    }
  }

  /**
   * Get count of rooms in certain hotel .
   *
   * @param hotelId - hotel id.
   * @return int - count of rooms in certain hotel.
   */
  @Override
  public int getRoomCount(int hotelId) {
    Session session = sessionFactory.openSession();
    return session
        .createQuery("select count (id) from Room where hotel.id = ?1", Long.class)
        .setParameter(1, hotelId)
        .uniqueResult()
        .intValue();
  }
  // Zakhar
  @Override
  public List<Integer> getAvaibleRoomsNumber(int hotelId, String dateStart, String dateEnd) {
    try (Session session = sessionFactory.openSession()) {
      List<Integer> numbersOfBookedRooms =
          session
              .createQuery(
                  "SELECT r.number FROM Hotel h JOIN h.rooms r join r.roomBooks rb"
                      + " WHERE (((rb.orderStart > ?1 AND rb.orderStart < ?2)"
                      + " OR (rb.orderStart < ?3 AND rb.orderEnd > ?4)"
                      + " OR (rb.orderEnd > ?5 AND rb.orderEnd < ?6)) AND h.id=?7)",
                  Integer.class)
              .setParameter(1, dateStart)
              .setParameter(2, dateEnd)
              .setParameter(3, dateStart)
              .setParameter(4, dateEnd)
              .setParameter(5, dateStart)
              .setParameter(6, dateEnd)
              .setParameter(7, hotelId)
              .list();

      List<Integer> numbersOfAvaibleRooms;
      if (numbersOfBookedRooms.size() == 0) {
        numbersOfAvaibleRooms =
            session
                .createQuery(
                    "SELECT r.number FROM Hotel h" + " JOIN h.rooms r " + "WHERE h.id =?1",
                    Integer.class)
                .setParameter(1, hotelId)
                .list();
      } else {
        numbersOfAvaibleRooms =
            session
                .createQuery(
                    "SELECT r.number FROM Hotel h"
                        + " JOIN h.rooms r "
                        + "WHERE ( r.number NOT IN (:bookedNumbers) "
                        + "AND h.id =?1)",
                    Integer.class)
                .setParameterList("bookedNumbers", numbersOfBookedRooms)
                .setParameter(1, hotelId)
                .list();
      }
      return numbersOfAvaibleRooms;
      //
    }
  }
  // Zakhar
  @Override
  public List<Integer> getRoomsId(int hotelId) {
    try (Session session = sessionFactory.openSession()) {
      List<Integer> roomsId =
          session
              .createQuery(
                  "SELECT r.id FROM Hotel h JOIN h.rooms r WHERE h.id = ?1", Integer.class)
              .setParameter(1, hotelId)
              .list();
      if (roomsId == null) {
        throw new FieldNotFoundException("There is no rooms in this hotel");
      }
      return roomsId;
    }
  }
  ///////////////////////////////////////////
  //  @Override
  //  public List<Room> test() {
  //    Session session = sessionFactory.getCurrentSession();
  //    List<Room> testList =
  //        session
  //            .createQuery(
  //                "select r from Room r where r.id not in (select r.id from room r where r.id>4)",
  //                Room.class)
  //            .list();
  //    testList.forEach(s -> System.out.print(s.getId() + "|"));
  //    System.out.println();
  //    return testList;
  //  }
}
