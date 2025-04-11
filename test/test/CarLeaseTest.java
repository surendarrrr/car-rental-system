package test;

import dao.ICarLeaseRepository;
import dao.ICarLeaseRepositoryImpl;
import entity.Customer;
import entity.Lease;
import entity.Vehicle;
import exception.CarNotFoundException;
import exception.CustomerNotFoundException;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Date;
import java.util.Calendar;
import java.util.List;

public class CarLeaseTest {
    private ICarLeaseRepository repository;

    @Before
    public void setUp() {
        repository = new ICarLeaseRepositoryImpl();

    }

    @Test
    public void testAddCar() {
        // Arrange
        Vehicle testCar = new Vehicle("Toyota", "Corolla", 2020, 50.0, "available", 5, 1800);
        int initialCarCount = repository.listAvailableCars().size();

        // Act
        repository.addCar(testCar);
        int finalCarCount = repository.listAvailableCars().size();

        // Assert
        assertEquals(initialCarCount + 1, finalCarCount);
    }

    @Test
    public void testCreateLease() throws CustomerNotFoundException, CarNotFoundException {
        // Arrange
        // First ensure we have a customer and car in the database
        Customer testCustomer = new Customer("John", "Doe", "john@example.com", "123456789");
        repository.addCustomer(testCustomer);
        int customerID = repository.listCustomers().get(repository.listCustomers().size() - 1).getCustomerID();

        Vehicle testCar = new Vehicle("Honda", "Civic", 2019, 45.0, "available", 5, 1600);
        repository.addCar(testCar);
        int carID = repository.listAvailableCars().get(repository.listAvailableCars().size() - 1).getVehicleID();

        // Create dates for lease
        Calendar calendar = Calendar.getInstance();
        Date startDate = calendar.getTime();
        calendar.add(Calendar.DATE, 7); // Add 7 days
        Date endDate = calendar.getTime();

        // Act
        Lease lease = repository.createLease(customerID, carID, startDate, endDate, "daily");

        // Assert
        assertNotNull("Lease should not be null", lease);
        assertEquals("Lease should have correct car ID", carID, lease.getVehicleID());
        assertEquals("Lease should have correct customer ID", customerID, lease.getCustomerID());
    }

    @Test
    public void testGetLeaseById() throws CustomerNotFoundException, CarNotFoundException {
        // Arrange
        // First create a lease to retrieve
        Customer testCustomer = new Customer("Jane", "Smith", "jane@example.com", "987654321");
        repository.addCustomer(testCustomer);
        int customerID = repository.listCustomers().get(repository.listCustomers().size() - 1).getCustomerID();

        Vehicle testCar = new Vehicle("Ford", "Focus", 2021, 55.0, "available", 5, 2000);
        repository.addCar(testCar);
        int carID = repository.listAvailableCars().get(repository.listAvailableCars().size() - 1).getVehicleID();

        // Create dates for lease
        Calendar calendar = Calendar.getInstance();
        Date startDate = calendar.getTime();
        calendar.add(Calendar.DATE, 5); // Add 5 days
        Date endDate = calendar.getTime();

        // Create lease
        Lease createdLease = repository.createLease(customerID, carID, startDate, endDate, "daily");

        // Act
        List<Lease> leases = repository.listLeaseHistory();
        boolean leaseFound = false;

        for (Lease lease : leases) {
            if (lease.getLeaseID() == createdLease.getLeaseID()) {
                leaseFound = true;
                break;
            }
        }

        // Assert
        assertTrue("Created lease should be found in lease history", leaseFound);
    }

    @Test(expected = CarNotFoundException.class)
    public void testCarNotFoundExceptionIsThrown() throws CarNotFoundException {
        // Act & Assert
        repository.findCarById(-99999); // This ID should not exist
    }
}