package model;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Customer {

  // Fixed "2. ⚠️ Including the final access modifiers on your data model class variables."
  private final String firstName;
  private final String lastName;
  private final String email;

  public Customer(String email, String firstName, String lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;

    String emailRegex = "^(.+)@(.+).(.+)$";
    Pattern pattern = Pattern.compile(emailRegex);

    // Now create matcher object.
    Matcher m = pattern.matcher(email);
    boolean matches = m.matches();
    if (!matches) {
      throw new IllegalArgumentException("Invalid email!");
    }
  }

  public String getEmail() {
    return email;
  }

  @Override
  public String toString() {
    return "Customer{" +
      "FirstName:'" + firstName + '\'' +
      ", LastName:'" + lastName + '\'' +
      ", Email:'" + email + '\'' +
      '}';
  }

  /* Fixed "1.⚠️ At least one example of the model classes Room, Customer, and Reservation
  overriding the equals and hashcode methods." */
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Customer customer = (Customer) o;
    return firstName.equals(customer.firstName) && lastName.equals(customer.lastName) && email.equals(customer.email);
  }

  @Override
  public int hashCode() {
    return Objects.hash(firstName, lastName, email);
  }
}
