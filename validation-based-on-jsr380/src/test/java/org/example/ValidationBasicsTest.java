package org.example;

import org.example.model.Address;
import org.example.model.Customer;
import org.example.model.Groups;
import org.example.model.OrderItem;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.groups.Default;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ValidationBasicsTest {

    private static Validator validator;

    @BeforeAll
    static void init() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("@Null and @NotNull with groups (Create vs Update)")
    void testNullNotNullWithGroups() {
        Address addr = new Address("1 Main", "Metropolis", "CA", "90210");
        Customer create = new Customer(null, "Alice", "alice@example.com", LocalDate.of(1990, 1, 1),
                Arrays.asList("vip"), addr);

        // id must be null on create -> valid
        Set<ConstraintViolation<Customer>> v1 = validator.validate(create, Groups.Create.class);
        assertTrue(v1.isEmpty(), "Create should be valid when id is null");

        // For update, id must be present
        Customer updateMissingId = new Customer(null, "Alice", "alice@example.com", LocalDate.of(1990, 1, 1),
                Arrays.asList("vip"), addr);
        Set<ConstraintViolation<Customer>> v2 = validator.validate(updateMissingId, Groups.Update.class);
        assertEquals(1, v2.size());
        ConstraintViolation<Customer> cv = v2.iterator().next();
        assertEquals("id", cv.getPropertyPath().toString());
    }

    @Test
    @DisplayName("Built-in constraints: @NotBlank, @Size, @Email, @Past, @Pattern, @Positive, @DecimalMin")
    void testBuiltinConstraints() {
        Address badAddr = new Address("", "", "California", "ABCDE"); // invalid: blanks, bad state, bad zip
        Customer c = new Customer(
                1L,
                "A", // too short
                "not-an-email",
                LocalDate.now().plusDays(1), // not in the past
                Arrays.asList("ok", " ", null), // blank + null elements
                badAddr
        );

        Set<ConstraintViolation<Customer>> violations = validator.validate(c, Default.class, Groups.Update.class);
        // Expect multiple violations across nested and container elements
        assertFalse(violations.isEmpty());

        // Collect paths for quick sanity check (no hard-coding messages)
        Set<String> paths = new HashSet<>();
        for (ConstraintViolation<Customer> v : violations) {
            paths.add(v.getPropertyPath().toString());
        }

        // Customer.name size
        assertTrue(paths.contains("name"));
        // Customer.email
        assertTrue(paths.contains("email"));
        // Customer.birthDate
        assertTrue(paths.contains("birthDate"));
        // Customer.tags[1] & tags[2]
        assertTrue(paths.stream().anyMatch(p -> p.startsWith("tags[")));
        // Nested Address fields
        assertTrue(paths.contains("address.line1"));
        assertTrue(paths.contains("address.city"));
        assertTrue(paths.contains("address.state"));
        assertTrue(paths.contains("address.zip"));
    }

    @Test
    @DisplayName("OrderItem: @NotBlank, @Positive, @DecimalMin")
    void testOrderItemConstraints() {
        OrderItem item = new OrderItem("", 0, new BigDecimal("0.0"));
        Set<ConstraintViolation<OrderItem>> violations = validator.validate(item);
        assertEquals(3, violations.size());
        Set<String> paths = new HashSet<>();
        for (ConstraintViolation<OrderItem> v : violations) {
            paths.add(v.getPropertyPath().toString());
        }
        assertTrue(paths.contains("sku"));
        assertTrue(paths.contains("quantity"));
        assertTrue(paths.contains("price"));
    }
}
