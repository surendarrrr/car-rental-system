package main;

import dao.ICarLeaseRepository;
import dao.ICarLeaseRepositoryImpl;
import entity.Customer;
import entity.Lease;
import entity.Payment;
import entity.Vehicle;
import exception.CarNotFoundException;
import exception.CustomerNotFoundException;
import exception.LeaseNotFoundException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class MainModule {
    private static ICarLeaseRepository repository = new ICarLeaseRepositoryImpl();
    private static Scanner scanner = new Scanner(System.in);
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static void main(String[] args) {
        boolean exit = false;

        System.out.println("=== Welcome to Car Rental System ===");

        while (!exit) {
            System.out.println("\nMain Menu:");
            System.out.println("1. Vehicle Management");
            System.out.println("2. Customer Management");
            System.out.println("3. Lease Management");
            System.out.println("4. Payment Management");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            int choice = getIntInput();

            switch (choice) {
                case 1:
                    vehicleManagementMenu();
                    break;
                case 2:
                    customerManagementMenu();
                    break;
                case 3:
                    leaseManagementMenu();
                    break;
                case 4:
                    paymentManagementMenu();
                    break;
                case 0:
                    exit = true;
                    System.out.println("Thank you for using Car Rental System. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }

    // Vehicle Management Menu
    private static void vehicleManagementMenu() {
        boolean back = false;

        while (!back) {
            System.out.println("\nVehicle Management:");
            System.out.println("1. Add New Vehicle");
            System.out.println("2. Remove Vehicle");
            System.out.println("3. List Available Vehicles");
            System.out.println("4. List Rented Vehicles");
            System.out.println("5. Find Vehicle by ID");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = getIntInput();

            switch (choice) {
                case 1:
                    addVehicle();
                    break;
                case 2:
                    removeVehicle();
                    break;
                case 3:
                    listAvailableVehicles();
                    break;
                case 4:
                    listRentedVehicles();
                    break;
                case 5:
                    findVehicleById();
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void addVehicle() {
        System.out.println("\nAdd New Vehicle:");

        System.out.print("Enter make: ");
        String make = scanner.nextLine();

        System.out.print("Enter model: ");
        String model = scanner.nextLine();

        System.out.print("Enter year: ");
        int year = getIntInput();

        System.out.print("Enter daily rate: ");
        double dailyRate = getDoubleInput();

        System.out.print("Enter passenger capacity: ");
        int passengerCapacity = getIntInput();

        System.out.print("Enter engine capacity (cc): ");
        int engineCapacity = getIntInput();

        Vehicle vehicle = new Vehicle(make, model, year, dailyRate, "available", passengerCapacity, engineCapacity);
        repository.addCar(vehicle);
    }

    private static void removeVehicle() {
        System.out.println("\nRemove Vehicle:");

        System.out.print("Enter vehicle ID: ");
        int vehicleID = getIntInput();

        try {
            repository.removeCar(vehicleID);
        } catch (CarNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void listAvailableVehicles() {
        System.out.println("\nAvailable Vehicles:");
        List<Vehicle> availableCars = repository.listAvailableCars();

        if (availableCars.isEmpty()) {
            System.out.println("No available vehicles found.");
        } else {
            displayVehicles(availableCars);
        }
    }

    private static void listRentedVehicles() {
        System.out.println("\nRented Vehicles:");
        List<Vehicle> rentedCars = repository.listRentedCars();

        if (rentedCars.isEmpty()) {
            System.out.println("No rented vehicles found.");
        } else {
            displayVehicles(rentedCars);
        }
    }

    private static void findVehicleById() {
        System.out.println("\nFind Vehicle by ID:");

        System.out.print("Enter vehicle ID: ");
        int vehicleID = getIntInput();

        try {
            Vehicle vehicle = repository.findCarById(vehicleID);
            System.out.println("\nVehicle Details:");
            System.out.println("ID: " + vehicle.getVehicleID());
            System.out.println("Make: " + vehicle.getMake());
            System.out.println("Model: " + vehicle.getModel());
            System.out.println("Year: " + vehicle.getYear());
            System.out.println("Daily Rate: $" + vehicle.getDailyRate());
            System.out.println("Status: " + vehicle.getStatus());
            System.out.println("Passenger Capacity: " + vehicle.getPassengerCapacity());
            System.out.println("Engine Capacity: " + vehicle.getEngineCapacity() + "cc");
        } catch (CarNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void displayVehicles(List<Vehicle> vehicles) {
        System.out.println("\n------------------------------------------------------------------");
        System.out.printf("%-5s %-10s %-10s %-5s %-10s %-15s %-8s %-8s%n",
                "ID", "Make", "Model", "Year", "Rate", "Status", "Capacity", "Engine");
        System.out.println("------------------------------------------------------------------");

        for (Vehicle vehicle : vehicles) {
            System.out.printf("%-5d %-10s %-10s %-5d $%-9.2f %-15s %-8d %-8d%n",
                    vehicle.getVehicleID(), vehicle.getMake(), vehicle.getModel(),
                    vehicle.getYear(), vehicle.getDailyRate(), vehicle.getStatus(),
                    vehicle.getPassengerCapacity(), vehicle.getEngineCapacity());
        }

        System.out.println("------------------------------------------------------------------");
    }

    // Customer Management Menu
    private static void customerManagementMenu() {
        boolean back = false;

        while (!back) {
            System.out.println("\nCustomer Management:");
            System.out.println("1. Add New Customer");
            System.out.println("2. Remove Customer");
            System.out.println("3. List All Customers");
            System.out.println("4. Find Customer by ID");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = getIntInput();

            switch (choice) {
                case 1:
                    addCustomer();
                    break;
                case 2:
                    removeCustomer();
                    break;
                case 3:
                    listAllCustomers();
                    break;
                case 4:
                    findCustomerById();
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void addCustomer() {
        System.out.println("\nAdd New Customer:");

        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();

        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();

        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        System.out.print("Enter phone number: ");
        String phoneNumber = scanner.nextLine();

        Customer customer = new Customer(firstName, lastName, email, phoneNumber);
        repository.addCustomer(customer);
    }

    private static void removeCustomer() {
        System.out.println("\nRemove Customer:");

        System.out.print("Enter customer ID: ");
        int customerID = getIntInput();

        try {
            repository.removeCustomer(customerID);
        } catch (CustomerNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void listAllCustomers() {
        System.out.println("\nAll Customers:");
        List<Customer> customers = repository.listCustomers();

        if (customers.isEmpty()) {
            System.out.println("No customers found.");
        } else {
            displayCustomers(customers);
        }
    }

    private static void findCustomerById() {
        System.out.println("\nFind Customer by ID:");

        System.out.print("Enter customer ID: ");
        int customerID = getIntInput();

        try {
            Customer customer = repository.findCustomerById(customerID);
            System.out.println("\nCustomer Details:");
            System.out.println("ID: " + customer.getCustomerID());
            System.out.println("Name: " + customer.getFirstName() + " " + customer.getLastName());
            System.out.println("Email: " + customer.getEmail());
            System.out.println("Phone: " + customer.getPhoneNumber());
        } catch (CustomerNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void displayCustomers(List<Customer> customers) {
        System.out.println("\n-----------------------------------------------------------");
        System.out.printf("%-5s %-15s %-15s %-25s %-15s%n",
                "ID", "First Name", "Last Name", "Email", "Phone");
        System.out.println("-----------------------------------------------------------");

        for (Customer customer : customers) {
            System.out.printf("%-5d %-15s %-15s %-25s %-15s%n",
                    customer.getCustomerID(), customer.getFirstName(), customer.getLastName(),
                    customer.getEmail(), customer.getPhoneNumber());
        }

        System.out.println("-----------------------------------------------------------");
    }

    // Lease Management Menu
    private static void leaseManagementMenu() {
        boolean back = false;

        while (!back) {
            System.out.println("\nLease Management:");
            System.out.println("1. Create New Lease");
            System.out.println("2. Return Car");
            System.out.println("3. List Active Leases");
            System.out.println("4. List Lease History");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = getIntInput();

            switch (choice) {
                case 1:
                    createLease();
                    break;
                case 2:
                    returnCar();
                    break;
                case 3:
                    listActiveLeases();
                    break;
                case 4:
                    listLeaseHistory();
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void createLease() {
        System.out.println("\nCreate New Lease:");

        System.out.print("Enter customer ID: ");
        int customerID = getIntInput();

        System.out.print("Enter vehicle ID: ");
        int vehicleID = getIntInput();

        System.out.print("Enter start date (yyyy-MM-dd): ");
        Date startDate = getDateInput();

        System.out.print("Enter end date (yyyy-MM-dd): ");
        Date endDate = getDateInput();

        System.out.print("Enter lease type (daily/monthly): ");
        String type = scanner.nextLine().toLowerCase();

        if (!type.equals("daily") && !type.equals("monthly")) {
            System.out.println("Invalid lease type. Type must be 'daily' or 'monthly'.");
            return;
        }

        try {
            Lease lease = repository.createLease(customerID, vehicleID, startDate, endDate, type);
            if (lease != null) {
                System.out.println("\nLease created with ID: " + lease.getLeaseID());
            }
        } catch (CustomerNotFoundException | CarNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void returnCar() {
        System.out.println("\nReturn Car:");

        System.out.print("Enter lease ID: ");
        int leaseID = getIntInput();

        try {
            repository.returnCar(leaseID);
        } catch (LeaseNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void listActiveLeases() {
        System.out.println("\nActive Leases:");
        List<Lease> activeLeases = repository.listActiveLeases();

        if (activeLeases.isEmpty()) {
            System.out.println("No active leases found.");
        } else {
            displayLeases(activeLeases);
        }
    }

    private static void listLeaseHistory() {
        System.out.println("\nLease History:");
        List<Lease> leaseHistory = repository.listLeaseHistory();

        if (leaseHistory.isEmpty()) {
            System.out.println("No lease history found.");
        } else {
            displayLeases(leaseHistory);
        }
    }

    private static void displayLeases(List<Lease> leases) {
        System.out.println("\n---------------------------------------------------------------------");
        System.out.printf("%-5s %-10s %-10s %-12s %-12s %-10s%n",
                "ID", "Vehicle ID", "Customer ID", "Start Date", "End Date", "Type");
        System.out.println("---------------------------------------------------------------------");

        SimpleDateFormat displayFormat = new SimpleDateFormat("yyyy-MM-dd");

        for (Lease lease : leases) {
            System.out.printf("%-5d %-10d %-10d %-12s %-12s %-10s%n",
                    lease.getLeaseID(), lease.getVehicleID(), lease.getCustomerID(),
                    displayFormat.format(lease.getStartDate()),
                    displayFormat.format(lease.getEndDate()),
                    lease.getType());
        }

        System.out.println("---------------------------------------------------------------------");
    }

    // Payment Management Menu
    private static void paymentManagementMenu() {
        boolean back = false;

        while (!back) {
            System.out.println("\nPayment Management:");
            System.out.println("1. Record Payment");
            System.out.println("2. View Customer Payment History");
            System.out.println("3. View Total Revenue");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = getIntInput();

            switch (choice) {
                case 1:
                    recordPayment();
                    break;
                case 2:
                    viewCustomerPaymentHistory();
                    break;
                case 3:
                    viewTotalRevenue();
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void recordPayment() {
        System.out.println("\nRecord Payment:");

        System.out.print("Enter lease ID: ");
        int leaseID = getIntInput();

        System.out.print("Enter payment amount: $");
        double amount = getDoubleInput();

        try {
            repository.recordPayment(leaseID, amount);
        } catch (LeaseNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void viewCustomerPaymentHistory() {
        System.out.println("\nView Customer Payment History:");

        System.out.print("Enter customer ID: ");
        int customerID = getIntInput();

        try {
            List<Payment> payments = repository.getPaymentHistory(customerID);

            if (payments.isEmpty()) {
                System.out.println("No payment history found for this customer.");
            } else {
                displayPayments(payments);
            }
        } catch (CustomerNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void viewTotalRevenue() {
        System.out.println("\nTotal Revenue:");
        double totalRevenue = repository.getTotalPayments();
        System.out.printf("Total revenue: $%.2f%n", totalRevenue);
    }

    private static void displayPayments(List<Payment> payments) {
        System.out.println("\n-------------------------------------------");
        System.out.printf("%-5s %-10s %-12s %-10s%n",
                "ID", "Lease ID", "Date", "Amount");
        System.out.println("-------------------------------------------");

        SimpleDateFormat displayFormat = new SimpleDateFormat("yyyy-MM-dd");

        for (Payment payment : payments) {
            System.out.printf("%-5d %-10d %-12s $%-9.2f%n",
                    payment.getPaymentID(), payment.getLeaseID(),
                    displayFormat.format(payment.getPaymentDate()),
                    payment.getAmount());
        }

        System.out.println("-------------------------------------------");
    }

    // Helper methods for input handling
    private static int getIntInput() {
        while (true) {
            try {
                String input = scanner.nextLine();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a number: ");
            }
        }
    }

    private static double getDoubleInput() {
        while (true) {
            try {
                String input = scanner.nextLine();
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a number: ");
            }
        }
    }

    private static Date getDateInput() {
        while (true) {
            try {
                String input = scanner.nextLine();
                return dateFormat.parse(input);
            } catch (ParseException e) {
                System.out.print("Invalid date format. Please enter date in format yyyy-MM-dd: ");
            }
        }
    }
}