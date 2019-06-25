package com.giraone.testdata;

public class EmployeeAddressChangeCommand extends AbstractEmployeeCommand {

    EmployeeAddressChangePayload payload;

    public EmployeeAddressChangeCommand(EmployeeAddressChangePayload payload) {
        this.payload = payload;
    }

    public String getType() {
        return "EmployeeAddressChangeCommand";
    }

    public EmployeeAddressChangePayload getPayload() {
        return payload;
    }

    @Override
    public String toString() {
        return "EmployeeAddressChangeCommand{" +
                "payload=" + payload +
                '}';
    }
}
