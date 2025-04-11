package dao;

import entity.*;
import exception.*;
import util.DBConnUtil;
import util.DBPropertyUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ICarLeaseRepositoryImpl implements ICarLeaseRepository {
    private Connection connection;

    public ICarLeaseRepositoryImpl() {
        String connectionString = DBPropertyUtil.getPropertyString("db.properties");
        this.connection = DBConnUtil.getConnection(connectionString);
    }

    // Car Management
    @Override
    public void addCar(Vehicle car) {
        String query = "INSERT INTO Vehicle (make, model, year, dailyRate, status, passengerCapacity, engineCapacity) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, car.getMake());
            pstmt.setString(2, car.getModel());
            pstmt.setInt(3, car.getYear());
            pstmt.setDouble(4, car.getDailyRate());
            pstmt.setString(5, car.getStatus());
            pstmt.setInt(6, car.getPassengerCapacity());
            pstmt.setInt(7, car.getEngineCapacity());

            pstmt.executeUpdate();
            System.out.println("Car added successfully!");

        } catch (SQLException e) {
            System.err.println("Error adding car: " + e.getMessage());
        }
    }

    @Override
    public void removeCar(int carID) throws CarNotFoundException {
        // First check if car exists
        findCarById(carID);

        String query = "DELETE FROM Vehicle WHERE vehicleID = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, carID);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Car removed successfully!");
            }

        } catch (SQLException e) {
            System.err.println("Error removing car: " + e.getMessage());
        }
    }

    @Override
    public List<Vehicle> listAvailableCars() {
        List<Vehicle> availableCars = new ArrayList<>();
        String query = "SELECT * FROM Vehicle WHERE status = 'available'";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Vehicle car = new Vehicle(
                        rs.getInt("vehicleID"),
                        rs.getString("make"),
                        rs.getString("model"),
                        rs.getInt("year"),
                        rs.getDouble("dailyRate"),
                        rs.getString("status"),
                        rs.getInt("passengerCapacity"),
                        rs.getInt("engineCapacity")
                );
                availableCars.add(car);
            }

        } catch (SQLException e) {
            System.err.println("Error listing available cars: " + e.getMessage());
        }

        return availableCars;
    }

    @Override
    public List<Vehicle> listRentedCars() {
        List<Vehicle> rentedCars = new ArrayList<>();
        String query = "SELECT * FROM Vehicle WHERE status = 'notAvailable'";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Vehicle car = new Vehicle(
                        rs.getInt("vehicleID"),
                        rs.getString("make"),
                        rs.getString("model"),
                        rs.getInt("year"),
                        rs.getDouble("dailyRate"),
                        rs.getString("status"),
                        rs.getInt("passengerCapacity"),
                        rs.getInt("engineCapacity")
                );
                rentedCars.add(car);
            }

        } catch (SQLException e) {
            System.err.println("Error listing rented cars: " + e.getMessage());
        }

        return rentedCars;
    }

    @Override
    public Vehicle findCarById(int carID) throws CarNotFoundException {
        String query = "SELECT * FROM Vehicle WHERE vehicleID = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, carID);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Vehicle(
                            rs.getInt("vehicleID"),
                            rs.getString("make"),
                            rs.getString("model"),
                            rs.getInt("year"),
                            rs.getDouble("dailyRate"),
                            rs.getString("status"),
                            rs.getInt("passengerCapacity"),
                            rs.getInt("engineCapacity")
                    );
                } else {
                    throw new CarNotFoundException("Car with ID " + carID + " not found");
                }
            }

        } catch (SQLException e) {
            System.err.println("Error finding car: " + e.getMessage());
            throw new CarNotFoundException("Error finding car: " + e.getMessage());
        }
    }

    // Customer Management
    @Override
    public void addCustomer(Customer customer) {
        String query = "INSERT INTO Customer (firstName, lastName, email, phoneNumber) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, customer.getFirstName());
            pstmt.setString(2, customer.getLastName());
            pstmt.setString(3, customer.getEmail());
            pstmt.setString(4, customer.getPhoneNumber());

            pstmt.executeUpdate();
            System.out.println("Customer added successfully!");

        } catch (SQLException e) {
            System.err.println("Error adding customer: " + e.getMessage());
        }
    }

    @Override
    public void removeCustomer(int customerID) throws CustomerNotFoundException {
        // First check if customer exists
        findCustomerById(customerID);

        String query = "DELETE FROM Customer WHERE customerID = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, customerID);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Customer removed successfully!");
            }

        } catch (SQLException e) {
            System.err.println("Error removing customer: " + e.getMessage());
        }
    }

    @Override
    public List<Customer> listCustomers() {
        List<Customer> customers = new ArrayList<>();
        String query = "SELECT * FROM Customer";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Customer customer = new Customer(
                        rs.getInt("customerID"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("email"),
                        rs.getString("phoneNumber")
                );
                customers.add(customer);
            }

        } catch (SQLException e) {
            System.err.println("Error listing customers: " + e.getMessage());
        }

        return customers;
    }

    @Override
    public Customer findCustomerById(int customerID) throws CustomerNotFoundException {
        String query = "SELECT * FROM Customer WHERE customerID = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, customerID);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Customer(
                            rs.getInt("customerID"),
                            rs.getString("firstName"),
                            rs.getString("lastName"),
                            rs.getString("email"),
                            rs.getString("phoneNumber")
                    );
                } else {
                    throw new CustomerNotFoundException("Customer with ID " + customerID + " not found");
                }
            }

        } catch (SQLException e) {
            System.err.println("Error finding customer: " + e.getMessage());
            throw new CustomerNotFoundException("Error finding customer: " + e.getMessage());
        }
    }

    // Lease Management
    @Override
    public Lease createLease(int customerID, int carID, Date startDate, Date endDate, String type)
            throws CustomerNotFoundException, CarNotFoundException {

        // Verify customer and car exist
        findCustomerById(customerID);
        Vehicle car = findCarById(carID);

        // Check if car is available
        if (!"available".equals(car.getStatus())) {
            System.err.println("Car is not available for lease");
            return null;
        }

        // Update car status
        String updateCarQuery = "UPDATE Vehicle SET status = 'notAvailable' WHERE vehicleID = ?";
        String insertLeaseQuery = "INSERT INTO Lease (vehicleID, customerID, startDate, endDate, type) VALUES (?, ?, ?, ?, ?)";

        try {
            // Set auto-commit to false for transaction
            connection.setAutoCommit(false);

            // Update car status
            try (PreparedStatement updateCarStmt = connection.prepareStatement(updateCarQuery)) {
                updateCarStmt.setInt(1, carID);
                updateCarStmt.executeUpdate();
            }

            // Create lease
            try (PreparedStatement insertLeaseStmt = connection.prepareStatement(insertLeaseQuery, Statement.RETURN_GENERATED_KEYS)) {
                insertLeaseStmt.setInt(1, carID);
                insertLeaseStmt.setInt(2, customerID);
                insertLeaseStmt.setDate(3, new java.sql.Date(startDate.getTime()));
                insertLeaseStmt.setDate(4, new java.sql.Date(endDate.getTime()));
                insertLeaseStmt.setString(5, type);

                insertLeaseStmt.executeUpdate();

                // Get generated lease ID
                try (ResultSet generatedKeys = insertLeaseStmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int leaseID = generatedKeys.getInt(1);

                        // Commit transaction
                        connection.commit();

                        System.out.println("Lease created successfully!");
                        return new Lease(leaseID, carID, customerID, startDate, endDate, type);
                    }
                }
            }

            // If we get here, something went wrong
            connection.rollback();
            return null;

        } catch (SQLException e) {
            try {
                // Rollback transaction on error
                connection.rollback();
            } catch (SQLException rollbackEx) {
                System.err.println("Error rolling back transaction: " + rollbackEx.getMessage());
            }

            System.err.println("Error creating lease: " + e.getMessage());
            return null;
        } finally {
            try {
                // Reset auto-commit
                connection.setAutoCommit(true);
            } catch (SQLException resetEx) {
                System.err.println("Error resetting auto-commit: " + resetEx.getMessage());
            }
        }
    }

    @Override
    public void returnCar(int leaseID) throws LeaseNotFoundException {
        String findLeaseQuery = "SELECT vehicleID FROM Lease WHERE leaseID = ?";
        String updateCarQuery = "UPDATE Vehicle SET status = 'available' WHERE vehicleID = ?";

        try {
            // Find lease
            int vehicleID;
            try (PreparedStatement findStmt = connection.prepareStatement(findLeaseQuery)) {
                findStmt.setInt(1, leaseID);

                try (ResultSet rs = findStmt.executeQuery()) {
                    if (rs.next()) {
                        vehicleID = rs.getInt("vehicleID");
                    } else {
                        throw new LeaseNotFoundException("Lease with ID " + leaseID + " not found");
                    }
                }
            }

            // Update car status
            try (PreparedStatement updateStmt = connection.prepareStatement(updateCarQuery)) {
                updateStmt.setInt(1, vehicleID);

                updateStmt.executeUpdate();
                System.out.println("Car returned successfully!");
            }

        } catch (SQLException e) {
            System.err.println("Error returning car: " + e.getMessage());
        }
    }

    @Override
    public List<Lease> listActiveLeases() {
        List<Lease> activeLeases = new ArrayList<>();
        String query = "SELECT * FROM Lease l JOIN Vehicle v ON l.vehicleID = v.vehicleID WHERE v.status = 'notAvailable'";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Lease lease = new Lease(
                        rs.getInt("leaseID"),
                        rs.getInt("vehicleID"),
                        rs.getInt("customerID"),
                        rs.getDate("startDate"),
                        rs.getDate("endDate"),
                        rs.getString("type")
                );
                activeLeases.add(lease);
            }

        } catch (SQLException e) {
            System.err.println("Error listing active leases: " + e.getMessage());
        }

        return activeLeases;
    }

    @Override
    public List<Lease> listLeaseHistory() {
        List<Lease> leaseHistory = new ArrayList<>();
        String query = "SELECT * FROM Lease";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Lease lease = new Lease(
                        rs.getInt("leaseID"),
                        rs.getInt("vehicleID"),
                        rs.getInt("customerID"),
                        rs.getDate("startDate"),
                        rs.getDate("endDate"),
                        rs.getString("type")
                );
                leaseHistory.add(lease);
            }

        } catch (SQLException e) {
            System.err.println("Error listing lease history: " + e.getMessage());
        }

        return leaseHistory;

    }

    // Payment Handling
    @Override
    public void recordPayment(int leaseID, double amount) throws LeaseNotFoundException {
        // First check if lease exists
        String checkLeaseQuery = "SELECT * FROM Lease WHERE leaseID = ?";
        String insertPaymentQuery = "INSERT INTO Payment (leaseID, paymentDate, amount) VALUES (?, ?, ?)";

        try {
            // Check if lease exists
            try (PreparedStatement checkStmt = connection.prepareStatement(checkLeaseQuery)) {
                checkStmt.setInt(1, leaseID);

                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (!rs.next()) {
                        throw new LeaseNotFoundException("Lease with ID " + leaseID + " not found");
                    }
                }
            }

            // Record payment
            try (PreparedStatement insertStmt = connection.prepareStatement(insertPaymentQuery)) {
                insertStmt.setInt(1, leaseID);
                insertStmt.setDate(2, new java.sql.Date(new Date().getTime())); // Current date
                insertStmt.setDouble(3, amount);

                insertStmt.executeUpdate();
                System.out.println("Payment recorded successfully!");
            }

        } catch (SQLException e) {
            System.err.println("Error recording payment: " + e.getMessage());
        }
    }

    @Override
    public List<Payment> getPaymentHistory(int customerID) throws CustomerNotFoundException {
        // First check if customer exists
        findCustomerById(customerID);

        List<Payment> payments = new ArrayList<>();
        String query = "SELECT p.* FROM Payment p " +
                "JOIN Lease l ON p.leaseID = l.leaseID " +
                "WHERE l.customerID = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, customerID);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Payment payment = new Payment(
                            rs.getInt("paymentID"),
                            rs.getInt("leaseID"),
                            rs.getDate("paymentDate"),
                            rs.getDouble("amount")
                    );
                    payments.add(payment);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error getting payment history: " + e.getMessage());
        }

        return payments;
    }

    @Override
    public double getTotalPayments() {
        double total = 0;
        String query = "SELECT SUM(amount) as total FROM Payment";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {
                total = rs.getDouble("total");
            }

        } catch (SQLException e) {
            System.err.println("Error calculating total payments: " + e.getMessage());
        }

        return total;
    }
}

