package com.giraone.testdata;

public abstract class AbstractEmployeeEvent {

    Person payload;

    public String commandName() {
        return this.getClass().getName();
    }

    public Person getPayload() {
        return payload;
    }
}
