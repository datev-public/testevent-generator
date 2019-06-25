package com.giraone.testdata.generator;

import com.giraone.testdata.Person;

import java.util.ArrayList;
import java.util.Random;

class PersonManager {

    private static final Random RANDOM = new Random();

    private PersonGenerator personGenerator;

    final ArrayList<Person> personArrayList = new ArrayList<>();

    public PersonManager(PersonGenerator generator) {
        this.personGenerator = generator;
    }

    public Person changeAddressOnRandomPerson() {

        final Person existingPerson = personArrayList.get(personArrayList.size() > 1 ? RANDOM.nextInt(personArrayList.size() - 1) : 0);
        personArrayList.remove(existingPerson);

        final Person changedPerson = personGenerator.randomPerson();

        final Person newPerson = existingPerson.clone();
        newPerson.setAdditionalField("postalCode", changedPerson.getAdditionalField("postalCode"));
        newPerson.setAdditionalField("city", changedPerson.getAdditionalField("city"));
        newPerson.setAdditionalField("streetAddress", changedPerson.getAdditionalField("streetAddress"));
        personArrayList.add(newPerson);

        return newPerson;
    }

    public Person changeIbanOnRandomPerson() {

        final Person existingPerson = personArrayList.get(personArrayList.size() > 1 ? RANDOM.nextInt(personArrayList.size() - 1) : 0);
        personArrayList.remove(existingPerson);

        final Person changedPerson = personGenerator.randomPerson();

        final Person newPerson = existingPerson.clone();
        newPerson.setAdditionalField("iban", changedPerson.getAdditionalField("iban"));
        personArrayList.add(newPerson);

        return newPerson;
    }

    public Person createAndAddPerson() {
        final Person newPerson = personGenerator.randomPerson();
        personArrayList.add(newPerson);
        return newPerson;
    }
}
