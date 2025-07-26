package org.example.model;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

public class Customer {

    @Null(groups = Groups.Create.class, message = "id must be null when creating")
    @NotNull(groups = Groups.Update.class, message = "id is required when updating")
    private Long id;

    @NotBlank
    @Size(min = 2, max = 40)
    private String name;

    @Email
    private String email;

    @Past
    private LocalDate birthDate;

    // container element constraint (BV 2.0)
    private List<@NotBlank String> tags;

    @Valid
    @NotNull
    private Address address;

    public Customer() {}

    public Customer(Long id, String name, String email, LocalDate birthDate, List<String> tags, Address address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.birthDate = birthDate;
        this.tags = tags;
        this.address = address;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public LocalDate getBirthDate() { return birthDate; }
    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }

    public List<String> getTags() { return tags; }
    public void setTags(List<String> tags) { this.tags = tags; }

    public Address getAddress() { return address; }
    public void setAddress(Address address) { this.address = address; }
}
