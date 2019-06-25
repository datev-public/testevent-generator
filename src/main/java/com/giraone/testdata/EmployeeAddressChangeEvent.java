package com.giraone.testdata;

public class EmployeeAddressChangeEvent extends AbstractEmployeeEvent {

    Person payload;

    public EmployeeAddressChangeEvent(Person payload) {
        this.payload = payload;
    }

    public String getType() {
        return "EmployeeAddressChangeEvent";
    }

    public Person getPayload() {
        return payload;
    }

    @Override
    public String toString() {
        return "EmployeeAddressChangeEvent{" +
                "payload=" + payload +
                '}';
    }
}
