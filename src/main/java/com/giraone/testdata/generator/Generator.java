package com.giraone.testdata.generator;

import com.giraone.testdata.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Generator {

    private static final Logger log = LogManager.getLogger(Generator.class);

    private static final Random RANDOM = new Random();

    // TODO: refactor: should be private, but the old tests would break
    final PersonManager personManager;


    private GeneratorConfiguration configuration;

    public Generator(GeneratorConfiguration configuration) {
        this.configuration = configuration;
        this.personManager = new PersonManager(new PersonGenerator(configuration));
    }

    public GeneratorConfiguration getConfiguration() {
        return configuration;
    }


    enum ACTION {
        NEW,
        CHANGE_ADDRESS,
        CHANGE_IBAN,
        LEAVE
    }

    private boolean firstAction = true;

    private ACTION nextAction() {

        if (firstAction) {
            firstAction = false;
            return ACTION.NEW;
        }

        final int DISTRIBUTION_NEW = 20;
        final int DISTRIBUTION_CHANGE = 78;

        final int DISTRIBUTION_CHANGE_ADDRESS = 30;
        final int DISTRIBUTION_CHANGE_IBAN = 70;


        int whatAction = RANDOM.nextInt(100);

        if (whatAction < DISTRIBUTION_NEW) {
            return ACTION.NEW;
        }
        if (whatAction > DISTRIBUTION_NEW &&
                whatAction < (DISTRIBUTION_NEW + DISTRIBUTION_CHANGE)) {

            int whatChange = RANDOM.nextInt(100);
            if (whatChange < DISTRIBUTION_CHANGE_ADDRESS) {
                return ACTION.CHANGE_ADDRESS;
            } else {
                return ACTION.CHANGE_IBAN;
            }
        }

        return ACTION.LEAVE;
    }

    public List<AbstractEmployeeCommand> randomCommands(int startIndex, int endIndex) {

        final ArrayList<AbstractEmployeeCommand> ret = new ArrayList<>();

        for (int index = startIndex; index < endIndex; index++) {

            switch (nextAction()) {
                case NEW: {
                    EmployeeJoinCompanyPayload payload = new EmployeeJoinCompanyPayload(personManager.createAndAddPerson());
                    EmployeeJoinCompanyCommand command = new EmployeeJoinCompanyCommand(payload);
                    ret.add(command);
                }
                case CHANGE_ADDRESS: {
                    EmployeeAddressChangeCommand command = new EmployeeAddressChangeCommand(new EmployeeAddressChangePayload(personManager.changeAddressOnRandomPerson()));
                    ret.add(command);
                }
                case CHANGE_IBAN: {
                    EmployeeIbanChangeCommand command = new EmployeeIbanChangeCommand(new EmployeeIbanChangePayload(personManager.changeIbanOnRandomPerson()));
                    ret.add(command);
                }
            }

        }
        return ret;
    }

    public List<AbstractEmployeeEvent> randomFullEvents(int startIndex, int endIndex) {

        final ArrayList<AbstractEmployeeEvent> ret = new ArrayList<>();

        for (int index = startIndex; index < endIndex; index++) {

            switch (nextAction()) {
                case NEW: {
                    EmployeeJoinedCompanyEvent event = new EmployeeJoinedCompanyEvent(personManager.createAndAddPerson());
                    ret.add(event);
                }
                case CHANGE_ADDRESS: {
                    final EmployeeAddressChangedEvent event = new EmployeeAddressChangedEvent(personManager.changeAddressOnRandomPerson());
                    ret.add(event);
                }
                case CHANGE_IBAN: {
                    final EmployeeIbanChangedEvent event = new EmployeeIbanChangedEvent(personManager.changeIbanOnRandomPerson());
                    ret.add(event);
                }
            }

        }
        return ret;
    }

}
