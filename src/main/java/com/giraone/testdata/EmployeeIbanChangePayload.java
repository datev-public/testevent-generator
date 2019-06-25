package com.giraone.testdata;

public class EmployeeIbanChangePayload {

    String id;
    String iban;

    public EmployeeIbanChangePayload(Person person) {
        this.id = person.id;
        this.iban = person.getAdditionalField("iban");

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }


    @Override
    public String toString() {
        return "EmployeeIbanChangePayload{" +
                ", id='" + id + '\'' +
                ", iban='" + iban + '}';
    }
}
