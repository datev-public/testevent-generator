package com.giraone.testdata;

public class EmployeeAddressChangePayload {

    String id;
    String city;
    String streetAddress;
    String postalCode;

    public EmployeeAddressChangePayload(Person person) {
        this.id = person.id;
        this.city = person.getAdditionalField("city");
        this.streetAddress = person.getAdditionalField("streetAddress");
        this.postalCode = person.getAdditionalField("postalCode");
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @Override
    public String toString() {
        return "EmployeeAddressChangePayload{" +
                ", id='" + id + '\'' +
                ", city='" + city + '\'' +
                ", streetAddress='" + streetAddress + '\'' +
                ", postalCode='" + postalCode + '\'' +
                '}';
    }
}
