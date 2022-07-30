package api;

import model.IRoom;
import model.Reservation;
import service.CustomerService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainMenu {
  private HotelResource hotelResource = new HotelResource();
  private AdminResource adminResource = new AdminResource();

  public void main() {
    Scanner scanner = new Scanner(System.in);
    try {
      System.out.println();
      System.out.println("Welcome to the Hotel Reservations Application!");
      System.out.println();
      System.out.println("_______________________________________________________");
      System.out.println("1. Find a reserve a room");
      System.out.println("2. See my reservations");
      System.out.println("3. Create an account");
      System.out.println("4. Admin");
      System.out.println("5. Exit");
      System.out.println("_______________________________________________________");
      System.out.println("Please select a number for the menu option:");
      String userInput = scanner.nextLine();

      // Fixed "6. ⚠️ The application UI uses a switch statement to handle the user input flow."
      switch(userInput) {
        case "1":
          FindAndReserv();
        case "2":
          seeMyReserv();
        case "3":
          createAccount();
          main();
        case "4":
          AdminMenu adminMenu=new AdminMenu();
          adminMenu.admin();
        case "5":
          scanner.close();
          System.exit(0);
        default:
          // Error Input Handling:
          while (!userInput.equals("1") && !userInput.equals("2") && !userInput.equals("3")
            && !userInput.equals("4") && !userInput.equals("5")) {
            System.out.println("Please select a number for the menu option:");
            userInput=scanner.nextLine();
          }
          switch (userInput) {
            case "1":
              FindAndReserv();
            case "2":
              seeMyReserv();
            case "3":
              createAccount();
              main();
            case "4":
              AdminMenu adminmenu=new AdminMenu();
              adminmenu.admin();
            case "5":
              scanner.close();
              System.exit(0);
          }
      }
    } catch (Exception ex) {
      ex.getLocalizedMessage();
    }

  }

  private void FindAndReserv() throws ParseException {
    // Take in user input:
    Scanner scanner = new Scanner(System.in);

    System.out.println("Enter your CheckIn Date (mm/dd/yyyy):");
    Date userCheckInTime = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH).parse(scanner.nextLine());
    System.out.println(userCheckInTime);

    System.out.println("Enter your CheckOut Date (mm/dd/yyyy):");
    Date userCheckOutTime = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH).parse(scanner.nextLine());
    System.out.println(userCheckOutTime);
    if (hotelResource.findARoom(userCheckInTime, userCheckOutTime).size() != 0) {
      for (IRoom room : hotelResource.findARoom(userCheckInTime, userCheckOutTime)) {
        System.out.println(room);
      }
    } else {
      /* Fixed "3. ⚠️ Make sure that users can book the different room numbers on the same dates and
      don't allow the user to book the same room twice for the same dates. */
      SimpleDateFormat sm = new SimpleDateFormat("MM/dd/yyyy");
      String suggIn = sm.format(hotelResource.getSuggestedDate(userCheckInTime));
      String suggOut = sm.format(hotelResource.getSuggestedDate(userCheckOutTime));
      System.out.println("All Rooms during this period of time are book, please use this suggested date:" + suggIn
        + " to " + suggOut);
      /* Fixed "4. ⚠️ Display the recommend rooms when all rooms are already booked for the hotel." */
      for (IRoom room : hotelResource.findSuggRoom(hotelResource.getSuggestedDate(userCheckInTime),
        hotelResource.getSuggestedDate(userCheckOutTime))) {
        System.out.println(room);
      }
      System.out.println("Enter your sugg CheckIn Date (mm/dd/yyyy):");
      userCheckInTime = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH).parse(scanner.nextLine());

      System.out.println("Enter your sugg CheckOut Date (mm/dd/yyyy):");
      userCheckOutTime = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH).parse(scanner.nextLine());
    }

    System.out.println("Would you like to book a room? y/n:");
    String yOrN = scanner.nextLine();
    if (yOrN.equalsIgnoreCase("y")) {

    } else if (yOrN.equalsIgnoreCase("n")) {
      main();
    } else {
      // User Input Errors Handling
      while(!yOrN.equalsIgnoreCase("y") && !yOrN.equalsIgnoreCase("n")) {
        System.out.println("Would you like to book a room? y/n:");
        yOrN = scanner.nextLine();
      }
      if (yOrN.equalsIgnoreCase("y")) {

      } else if (yOrN.equalsIgnoreCase("n")) {
        main();
      }
    }

    System.out.println("Do you have an account with us? y/n:");
    yOrN = scanner.nextLine();
    if (yOrN.equalsIgnoreCase("y")) {

    } else if (yOrN.equalsIgnoreCase("n")) {
      createAccount();
    } else {
      // User Input Errors Handling
      while(!yOrN.equalsIgnoreCase("y") && !yOrN.equalsIgnoreCase("n")) {
        System.out.println("Do you have an account with us? y/n:");
        yOrN = scanner.nextLine();
      }
      if (yOrN.equalsIgnoreCase("y")) {

      } else if (yOrN.equalsIgnoreCase("n")) {
        createAccount();
      }
    }

    // Get User Email
    System.out.println("Enter your Email (Format: name@domain.com):");
    String userEmail = scanner.nextLine();
    // User Input Error Handling (Invalid Email)
    String emailRegex = "^(.+)@(.+).(.+)$";
    Pattern pattern = Pattern.compile(emailRegex);
    Matcher m = pattern.matcher(userEmail);
    while (!m.matches()) {
     System.out.println("Please Enter a valid Email! (Format: name@domain.com):");
      userEmail = scanner.nextLine();
      m = pattern.matcher(userEmail);
    }
    // User Input Error Handling (Not Exist Email)
    if (hotelResource.getCustomer(userEmail) == null) {
      while (hotelResource.getCustomer(userEmail) == null) {
        System.out.println("Your account does not exist! Enter an email that exists (Format: name@domain.com): ");
        userEmail = scanner.nextLine();
      }
    }

    // Get the Room Number
    System.out.println("What is the room number of the room that you want to reserve?");
    String roomNumber = scanner.nextLine();

    /* Fixed "⚠️ Make sure that users can book the different room numbers on the same dates
    and don't allow the user to book the same room twice for the same dates." */
    if (hotelResource.findARoom(userCheckInTime, userCheckOutTime).contains(hotelResource.getRoom(roomNumber))) {
      hotelResource.bookARoom(userEmail, hotelResource.getRoom(roomNumber), userCheckInTime, userCheckOutTime);
    } else {
      // User Error Input Handling
      while (!hotelResource.findARoom(userCheckInTime, userCheckOutTime).contains(hotelResource.getRoom(roomNumber))) {
        System.out.println("Please enter a valid room number:");
        roomNumber = scanner.nextLine();
      }
      hotelResource.bookARoom(userEmail, hotelResource.getRoom(roomNumber), userCheckInTime, userCheckOutTime);
    }

    main();
  }

  private void seeMyReserv() {
    Scanner scanner = new Scanner(System.in);

    // Get User Email
    System.out.println("Enter your Email (Format: name@domain.com):");
    String userEmail = scanner.nextLine();
    // User Input Error Handling (Invalid Email)
    String emailRegex = "^(.+)@(.+).(.+)$";
    Pattern pattern = Pattern.compile(emailRegex);
    Matcher m = pattern.matcher(userEmail);
    while (!m.matches()) {
      System.out.println("Please Enter a valid Email! (Format: name@domain.com):");
      userEmail = scanner.nextLine();
      m = pattern.matcher(userEmail);
    }

    for (Reservation reservation : hotelResource.getCustomersReservations(userEmail)) {
      System.out.println(reservation);
    }

    main();
  }

  private void createAccount() {

    // Take in user input:
    Scanner scanner = new Scanner(System.in);
    System.out.println("Enter your Email (Format: name@domain.com)");
    String userEmail = scanner.nextLine();
    System.out.println("First Name:");
    String userFirstName = scanner.nextLine();
    System.out.println("Last Name:");
    String userLastName = scanner.nextLine();

    // Create an Account
    hotelResource.createACustomer(userEmail, userFirstName, userLastName);
  }

}
