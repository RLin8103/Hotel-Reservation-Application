package api;

import api.MainMenu;
import com.sun.tools.javac.Main;
import model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static model.RoomType.DOUBLE;
import static model.RoomType.SINGLE;

public class AdminMenu {
  private AdminResource adminResource = new AdminResource();
  private HotelResource hotelResource = new HotelResource();
  private List<IRoom> rooms = new ArrayList<>();
  private List<String> roomNumbers = new ArrayList<>();

  public void admin() {

    Scanner scanner = new Scanner(System.in);
    try {
      System.out.println();
      System.out.println("AdminMenu");
      System.out.println("_______________________________________________________");
      System.out.println("1. See all Customers");
      System.out.println("2. See all Rooms");
      System.out.println("3. See all Reservations");
      System.out.println("4. Add a Room");
      System.out.println("5. Back to Main Menu");
      System.out.println("_______________________________________________________");
      System.out.println("Please select a number for the menu option:");
      String userInput = scanner.nextLine();

      // Fixed "6. ⚠️ The application UI uses a switch statement to handle the user input flow."
      switch (userInput) {
        case "1":
          for (Customer customer : adminResource.getAllCustomers()) {
            System.out.println(customer);
          }
          ;
          admin();
        case "2":
          for (IRoom room : adminResource.getAllRooms()) {
            System.out.println(room);
          }
          admin();
        case "3":
          adminResource.displayAllReservations();
          admin();
        case "4":
          addRoom();
        case "5":
          MainMenu mainMenu=new MainMenu();
          mainMenu.main();
        default:
          // Error Input Handling:
          while (!userInput.equals("1") && !userInput.equals("2") && !userInput.equals("3")
            && !userInput.equals("4") && !userInput.equals("5")) {
            System.out.println("Please select a number for the menu option:");
            userInput=scanner.nextLine();
          }
          switch (userInput) {
            case "1":
              for (Customer customer : adminResource.getAllCustomers()) {
                System.out.println(customer);
              }
              ;
              admin();
            case "2":
              for (IRoom room : adminResource.getAllRooms()) {
                System.out.println(room);
              }
              ;
              admin();
            case "3":
              adminResource.displayAllReservations();
              admin();
            case "4":
              addRoom();
            case "5":
              MainMenu mainmenu=new MainMenu();
              mainmenu.main();
          }
      }
    } catch (Exception ex) {
      ex.getLocalizedMessage();
    }
  }

  private void addRoom() {
    Scanner scanner = new Scanner(System.in);

    // Add a room
    System.out.println("Enter the Room Number:");
    String roomNumber = scanner.nextLine();
    /* Fixed "Issue 1 ⚠️ Take a look at the below application UI snippet.
     I'm able to add the same room number multiple times.
     Adding the same room number multiple times can be frustrated for the user of the application
     and also bad for the business reputation." */
    while (hotelResource.getRoom(roomNumber) != null || roomNumbers.contains(roomNumber)) {
      System.out.println("This Room Number already exists. Please enter another Room Number:");
      roomNumber = scanner.nextLine();
    }
    roomNumbers.add(roomNumber);

    System.out.println("Enter the price:");
    double roomPrice = scanner.nextDouble();
    System.out.println("Enter 1 for single and 2 for double");
    int roomType = scanner.nextInt();
    if (roomType == 1) {
      Room room = new Room(roomNumber, roomPrice, SINGLE);
      rooms.add(room);
    } else if (roomType == 2) {
      Room room = new Room(roomNumber, roomPrice, DOUBLE);
      rooms.add(room);
    }

    // Add another room
    System.out.println("Do you want to add another room? (y or n)");
    String yOrN = scanner.nextLine();
    // Input Error Handling
    while (!yOrN.equalsIgnoreCase("y") && !yOrN.equalsIgnoreCase("n")) {
      yOrN = scanner.nextLine();
      System.out.println("Please Enter (y or n)");
    }
    if (yOrN.equalsIgnoreCase("y")) {
      addRoom();
    }

    adminResource.addRoom(rooms);
    rooms.clear();
    admin();
  }

  private boolean roomExists(String roomNumber) {
    for (IRoom room : adminResource.getAllRooms()) {
      if (room.getRoomNumber().equals(roomNumber)) {
        return true;
      }
    }
    return false;
  }
}
