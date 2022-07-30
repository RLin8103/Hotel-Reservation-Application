package service;

import model.Customer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomerService {

  /* Static Reference */
  static CustomerService customerService = new CustomerService();
  private CustomerService() {

  }
  public static CustomerService getInstance() {
    return customerService;
  }

  private List<Customer> customers = new ArrayList<>();

  public void addCustomer(String email, String firstName, String lastName) {
    Customer customer = new Customer(email, firstName, lastName);
    customers.add(customer);
  }

  public Customer getCustomer(String customerEmail) {
    Customer customer = null;

    for (int i = 0; i < customers.size(); i++) {
      if (customers.get(i).getEmail().equals(customerEmail)) {
        customer = customers.get(i);
      }
    }

    return customer;
  }

  public Collection<Customer> getAllCustomers() {
    return customers;
  }
}
