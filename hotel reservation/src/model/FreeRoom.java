package model;

public class FreeRoom extends Room{

  public FreeRoom(String roomNumber, double price, RoomType enumeration) {
    super(roomNumber, 0, enumeration);
  }

  @Override
  public String toString() {
    return super.toString();
  }
}
