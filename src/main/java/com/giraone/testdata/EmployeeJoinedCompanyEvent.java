package com.giraone.testdata;

public class EmployeeJoinedCompanyEvent extends AbstractEmployeeEvent {

    Person payload;

    public EmployeeJoinedCompanyEvent(Person payload) {
        this.payload = payload;
    }

    public String getType() {
        return "EmployeeJoinedCompanyEvent";
    }

    public Person getPayload() {
        return payload;
    }

    @Override
    public String toString() {
        return "EmployeeJoinedCompanyEvent{" +
                "payload=" + payload +
                '}';
    }
}
