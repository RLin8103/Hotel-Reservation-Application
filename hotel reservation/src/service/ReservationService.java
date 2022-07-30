package service;

import model.Customer;
import model.IRoom;
import model.Reservation;
import model.Room;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import java.util.*;

public class ReservationService {

  /* Static Reference */
  static ReservationService reservationService = new ReservationService();
  private ReservationService() {

  }
  public static ReservationService getInstance() {
    return reservationService;
  }

  private List<IRoom> rooms = new ArrayList<>();
  private List<Reservation> reservations = new ArrayList<>();

  public void addRoom(IRoom room) {
    rooms.add(room);
  }

  public IRoom getARoom(String roomId) {
    IRoom room = null;

    for (int i = 0; i < rooms.size(); i++) {
      if (rooms.get(i).getRoomNumber().equals(roomId)) {
        room = rooms.get(i);
        break;
      }
    }

    return room;
  }

  public Collection<IRoom> getAllRooms() {
    return rooms;
  }

  public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
    Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);
    reservations.add(reservation);
    return reservation;
  }

  public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
    List<IRoom> bookedRooms=new ArrayList<>();
    List<IRoom> availableRooms=new ArrayList<>();

    for (int i=0; i < reservations.size(); i++) {
      // Find rooms already booked during user booked time
      if (checkInDate.after(reservations.get(i).getCheckInDate()) && checkInDate.before(reservations.get(i).getCheckOutDate())
        || checkOutDate.after(reservations.get(i).getCheckInDate()) && checkOutDate.before(reservations.get(i).getCheckOutDate())) {
        bookedRooms.add(reservations.get(i).getRoom());
      } else if (checkInDate.equals(reservations.get(i).getCheckInDate()) || checkInDate.equals(reservations.get(i).getCheckOutDate())
        || checkOutDate.equals(reservations.get(i).getCheckInDate()) || checkOutDate.equals(reservations.get(i).getCheckOutDate())) {
        bookedRooms.add(reservations.get(i).getRoom());
      }
    }

    for (IRoom room : rooms) {
      if (!bookedRooms.contains(room)) {
        availableRooms.add(room);
      }
    }

    return availableRooms;
  }

  public static Calendar DateToCalendar(Date date) {
    Calendar cal = null;
    try {
      DateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
//      DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
      date = (Date)formatter.parse(date.toString());
      cal=Calendar.getInstance();
      cal.setTime(date);
    }
    catch (ParseException e)  {
        System.out.println("DateToCalendar() Exception: " + e);
    }

    return cal;
  }


  public Date suggestedDate(Date date) {
    Calendar newDate = DateToCalendar(date);
    newDate.add(Calendar.WEEK_OF_YEAR, 1);
    Date suggDate = newDate.getTime();

    return suggDate;
  }

  public Collection<IRoom> suggestedRooms(Date checkInDate, Date checkOutDate) {
    return findRooms(suggestedDate(checkInDate), suggestedDate(checkOutDate));
  }

  public Collection<Reservation> getCustomerReservation(Customer customer) {
    List<Reservation> customerReservation = new ArrayList<>();

    for (int i = 0; i <reservations.size(); i++) {
      /* Fixed "1.⚠️ At least one example of the model classes Room, Customer, and Reservation
      overriding the equals and hashcode methods." */
      if (reservations.get(i).getCustomer().equals(customer)) {
        customerReservation.add(reservations.get(i));
      }
    }

    return customerReservation;
  }

  public void printAllReservation() {
    if(reservations.isEmpty()){
      System.out.println("No reservations made\n\n");
    }
    else {
      // Fixed "5. ⚠️ Make sure to use the default access method for the reservation service class."
      printReservedRooms();
    }
  }

  void printReservedRooms(){
    for (Reservation reservation : reservations) {
      System.out.println(reservation);
    }
  }
}
