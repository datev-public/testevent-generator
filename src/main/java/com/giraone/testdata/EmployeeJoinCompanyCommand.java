package com.giraone.testdata;

public class EmployeeJoinCompanyCommand extends AbstractEmployeeCommand {

    EmployeeJoinCompanyPayload payload;

    public EmployeeJoinCompanyCommand(EmployeeJoinCompanyPayload payload) {
        this.payload = payload;
    }

    public String getType() {
        return "EmployeeJoinCompanyCommand";
    }

    public EmployeeJoinCompanyPayload getPayload() {
        return payload;
    }

    @Override
    public String toString() {
        return "EmployeeJoinCompanyCommand{" +
                "payload=" + payload +
                '}';
    }
}
