package com.giraone.testdata;

public class EmployeeIbanChangeCommand extends AbstractEmployeeCommand {

    EmployeeIbanChangePayload payload;

    public EmployeeIbanChangeCommand(EmployeeIbanChangePayload payload) {
        this.payload = payload;
    }

    public String getType() {
        return "EmployeeIbanChangeCommand";
    }

    public EmployeeIbanChangePayload getPayload() {
        return payload;
    }

    @Override
    public String toString() {
        return "EmployeeIbanChangeCommand{" +
                "payload=" + payload +
                '}';
    }
}
