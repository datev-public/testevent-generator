package com.giraone.testdata;

import com.giraone.testdata.generator.EnumGender;

public class EmployeeJoinCompanyPayload {

    String id;
    String givenName;
    String surname;
    EnumGender gender;
    String dateOfBirth;
    String city;
    String streetAddress;
    String postalCode;

    public EmployeeJoinCompanyPayload(Person person) {
        this.id = person.id;
        this.givenName = person.givenName;
        this.surname = person.surname;
        this.gender = person.gender;
        this.dateOfBirth = person.getAdditionalField("dateOfBirth");
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

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public EnumGender getGender() {
        return gender;
    }

    public void setGender(EnumGender gender) {
        this.gender = gender;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
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
        return "EmployeeJoinCompanyCommand{" +
                ", id='" + id + '\'' +
                ", givenName='" + givenName + '\'' +
                ", surname='" + surname + '\'' +
                ", gender=" + gender +
                ", city='" + city + '\'' +
                ", streetAddress='" + streetAddress + '\'' +
                ", postalCode='" + postalCode + '\'' +
                '}';
    }
}
