package com.giraone.testdata;

public abstract class AbstractEmployeeCommand {

    String id;

    public String commandName() {
        return this.getClass().getName();
    }
}
