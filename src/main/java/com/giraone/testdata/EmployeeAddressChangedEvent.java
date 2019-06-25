package com.giraone.testdata;

public class EmployeeAddressChangedEvent extends AbstractEmployeeEvent {

    Person payload;

    public EmployeeAddressChangedEvent(Person payload) {
        this.payload = payload;
    }

    public String getType() {
        return "EmployeeAddressChangedEvent";
    }

    public Person getPayload() {
        return payload;
    }

    @Override
    public String toString() {
        return "EmployeeAddressChangedEvent{" +
                "payload=" + payload +
                '}';
    }
}
