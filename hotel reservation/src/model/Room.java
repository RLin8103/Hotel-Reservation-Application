package model;

import java.util.Objects;

public class Room implements IRoom {
  protected String roomNumber;
  protected double price;
  protected RoomType enumeration;

  public Room(String roomNumber, double price, RoomType enumeration) {
    this.roomNumber = roomNumber;
    this.price = price;
    this.enumeration = enumeration;
  }

  @Override
  public String getRoomNumber() {
    return roomNumber;
  }

  @Override
  public RoomType getRoomType() {
    return enumeration;
  }

  @Override
  public double getRoomPrice() {
    return price;
  }

  @Override
  public boolean isFree() {
    return false;
  }

  @Override
  public String toString() {
    return "Room{" +
      "Room Number:'" + roomNumber + '\'' +
      ", Price:" + price +
      ", Type:" + enumeration +
      '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Room room=(Room) o;
    return Double.compare(room.price, price) == 0 && Objects.equals(roomNumber, room.roomNumber) && enumeration == room.enumeration;
  }

  @Override
  public int hashCode() {
    return Objects.hash(roomNumber, price, enumeration);
  }
}
