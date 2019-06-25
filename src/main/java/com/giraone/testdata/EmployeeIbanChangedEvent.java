package com.giraone.testdata;

public class EmployeeIbanChangedEvent extends AbstractEmployeeEvent {

    Person payload;

    public EmployeeIbanChangedEvent(Person payload) {
        this.payload = payload;
    }

    public String getType() {
        return "EmployeeIbanChangedEvent";
    }

    public Person getPayload() {
        return payload;
    }

    @Override
    public String toString() {
        return "EmployeeIbanChangedEvent{" +
                "payload=" + payload +
                '}';
    }
}
