package com.retailpro.config;

import com.retailpro.model.*;
import com.retailpro.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final SupplierRepository supplierRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DataInitializer(UserRepository userRepository,
                           CategoryRepository categoryRepository,
                           ProductRepository productRepository,
                           CustomerRepository customerRepository,
                           SupplierRepository supplierRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
        this.supplierRepository = supplierRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            logger.info("Initializing database with seed data...");

            // Create Users
            User admin = new User("admin",
                    passwordEncoder.encode("admin123"),
                    "Administrator",
                    UserRole.ADMIN);
            userRepository.save(admin);

            User cashier = new User("cashier",
                    passwordEncoder.encode("cashier123"),
                    "John Cashier",
                    UserRole.CASHIER);
            userRepository.save(cashier);

            logger.info("Created users: admin and cashier");

            // Create Categories
            Category electronics = new Category("Electronics",
                    "Electronic devices and accessories");
            Category food = new Category("Food & Beverages",
                    "Food items and drinks");
            Category clothing = new Category("Clothing",
                    "Apparel and accessories");

            categoryRepository.save(electronics);
            categoryRepository.save(food);
            categoryRepository.save(clothing);

            logger.info("Created {} categories", categoryRepository.count());

            // Create Products
            Product laptop = new Product("Laptop HP", "LAP001", electronics,
                    new BigDecimal("899.99"), 15);
            Product mouse = new Product("Wireless Mouse", "MOU001", electronics,
                    new BigDecimal("29.99"), 50);
            Product keyboard = new Product("Mechanical Keyboard", "KEY001",
                    electronics, new BigDecimal("79.99"), 30);

            Product coffee = new Product("Coffee Beans 1kg", "COF001", food,
                    new BigDecimal("15.99"), 100);
            Product tea = new Product("Green Tea Box", "TEA001", food,
                    new BigDecimal("8.99"), 80);

            Product tshirt = new Product("Cotton T-Shirt", "TSH001", clothing,
                    new BigDecimal("19.99"), 60);
            Product jeans = new Product("Denim Jeans", "JEA001", clothing,
                    new BigDecimal("49.99"), 40);

            productRepository.save(laptop);
            productRepository.save(mouse);
            productRepository.save(keyboard);
            productRepository.save(coffee);
            productRepository.save(tea);
            productRepository.save(tshirt);
            productRepository.save(jeans);

            logger.info("Created {} products", productRepository.count());

            // Create Customers
            Customer customer1 = new Customer("Alice Johnson",
                    "+1234567890",
                    "alice@example.com",
                    "123 Main St, City");
            Customer customer2 = new Customer("Bob Smith",
                    "+1987654321",
                    "bob@example.com",
                    "456 Oak Ave, Town");

            customerRepository.save(customer1);
            customerRepository.save(customer2);

            logger.info("Created {} customers", customerRepository.count());

            // Create Suppliers
            Supplier supplier1 = new Supplier("Tech Supplies Co.",
                    "+1122334455",
                    "sales@techsupplies.com",
                    "789 Industrial Park");
            Supplier supplier2 = new Supplier("Food Distributors Inc.",
                    "+1555666777",
                    "orders@fooddist.com",
                    "321 Warehouse Blvd");

            supplierRepository.save(supplier1);
            supplierRepository.save(supplier2);

            logger.info("Created {} suppliers", supplierRepository.count());

            logger.info("Database initialization completed successfully!");
        } else {
            logger.info("Database already contains data. Skipping initialization.");
        }
    }
}