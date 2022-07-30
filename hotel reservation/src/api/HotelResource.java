package api;

import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.Date;

public class HotelResource {

  CustomerService customerService = CustomerService.getInstance();
  ReservationService reservationService = ReservationService.getInstance();

  public Customer getCustomer(String email) {
    return customerService.getCustomer(email);
  }

  public void createACustomer(String email, String firstName, String lastName) {
    customerService.addCustomer(email, firstName, lastName);
  }

  public IRoom getRoom(String roomNumber) {
    return reservationService.getARoom(roomNumber);
  }

  public Reservation bookARoom(String customerEmail, IRoom room, Date checkInDate, Date checkOutDate) {
    return reservationService.reserveARoom(customerService.getCustomer(customerEmail), room, checkInDate, checkOutDate);
  }

  public Collection<Reservation> getCustomersReservations(String customerEmail) {
    return reservationService.getCustomerReservation(customerService.getCustomer(customerEmail));
  }

  public Collection<IRoom> findARoom(Date checkIn, Date checkOut) {
    return reservationService.findRooms(checkIn, checkOut);
  }

  public Collection<IRoom> findSuggRoom(Date checkIn, Date checkOut) {
    return reservationService.suggestedRooms(checkIn, checkOut);
  }

  public Date getSuggestedDate(Date date) {
    return reservationService.suggestedDate(date);
  }
}
