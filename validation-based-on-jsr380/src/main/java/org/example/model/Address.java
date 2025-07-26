package org.example.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class Address {
    @NotBlank
    private String line;

    @NotBlank
    private String city;

    @NotBlank
    @Pattern(regexp = "[A-Z]{2}", message = "State must be a 2-letter code")
    private String state;

    @NotBlank
    @Pattern(regexp = "\\d{5}", message = "ZIP must be 5 digits")
    private String zip;

    public Address() {}

    public Address(String line, String city, String state, String zip) {
        this.line = line;
        this.city = city;
        this.state = state;
        this.zip = zip;
    }

    public String getLine() { return line; }
    public void setLine(String line) { this.line = line; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public String getZip() { return zip; }
    public void setZip(String zip) { this.zip = zip; }
}
