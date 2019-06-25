package com.giraone.testdata.generator;

import com.giraone.testdata.*;
import com.giraone.testdata.fields.FieldEnhancer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

public class Generator {

    private static final Logger log = LogManager.getLogger(Generator.class);

    // TODO: 2 modes: read from resources and read from local directory
    private static final String DATA_PATH = "./src/main/resources/data";

    private static final Random RANDOM = new Random();

    private static final HashMap<String, ArrayList<String>> RANDOM_FROM_FILE = new HashMap<>();
    private static final HashMap<String, ArrayList<String>> RANDOM_FROM_WEIGHTED_FILE = new HashMap<>();
    private static final HashMap<String, HashMap<Integer, Integer>> WEIGHT_FROM_WEIGHTED_FILE = new HashMap<>();

    //- members --------------------------------------------------------------------------------------------------------

    private GeneratorConfiguration configuration;

    //- Constructor and getter/setters ---------------------------------------------------------------------------------

    public Generator(GeneratorConfiguration configuration) {
        this.configuration = configuration;
    }

    public GeneratorConfiguration getConfiguration() {
        return configuration;
    }

    //- PUBLIC ---------------------------------------------------------------------------------------------------------

    public int getNumberOfEntriesGivenName(EnumGender gender) {
        return valueListFromWeightedFile(getFileForGivenName(gender)).size();

    }

    public int getNumberOfEntriesSurname() {
        return valueListFromWeightedFile(getFileForSurname()).size();

    }

    public Person randomPerson() {

        final EnumGender gender = randomGender();
        final Person person = new Person(randomGivenName(gender), randomSurname(), gender);
        person.setId(UUID.randomUUID().toString());

        for (Map.Entry<String, FieldEnhancer> field : configuration.additionalFields.entrySet()) {
            field.getValue().addFields(configuration, field.getKey(), person);
        }

        return person;
    }

    public List<AbstractEmployeeCommand> randomCommands(int startIndex, int endIndex) {

        String[] lookup = new String[endIndex - startIndex];
        int totalCounter = 0;

        final ArrayList<AbstractEmployeeCommand> ret = new ArrayList<>();
        for (int index = startIndex; index < endIndex; index++) {

            int r = RANDOM.nextInt(100);
            if ((index - startIndex) < 2 || r < 20) {
                final Person person = randomPerson();
                EmployeeJoinCompanyPayload payload = new EmployeeJoinCompanyPayload(person);
                EmployeeJoinCompanyCommand command = new EmployeeJoinCompanyCommand(payload);
                ret.add(command);
                lookup[totalCounter++] = person.getId();
            } else {
                final Person person = randomPerson();
                final String id = lookup[RANDOM.nextInt(totalCounter)];
                person.setId(id);
                EmployeeAddressChangePayload payload = new EmployeeAddressChangePayload(person);
                EmployeeAddressChangeCommand command = new EmployeeAddressChangeCommand(payload);
                ret.add(command);
            }
        }
        return ret;
    }

    public List<AbstractEmployeeEvent> randomFullEvents(int startIndex, int endIndex) {

        int totalCounter = 0;
        final ArrayList<AbstractEmployeeEvent> ret = new ArrayList<>();
        for (int index = startIndex; index < endIndex; index++) {

            int r = RANDOM.nextInt(100);
            if ((index - startIndex) < 2 || r < 20) {
                final Person person = randomPerson();
                EmployeeJoinedCompanyEvent event = new EmployeeJoinedCompanyEvent(person);
                totalCounter++;
                ret.add(event);
            } else {
                final Person changedPerson = randomPerson();
                final AbstractEmployeeEvent event = ret.get(RANDOM.nextInt(totalCounter));
                final Person existingPerson = event.getPayload().clone();
                existingPerson.setAdditionalField("postalCode", changedPerson.getAdditionalField("postalCode"));
                existingPerson.setAdditionalField("city", changedPerson.getAdditionalField("city"));
                existingPerson.setAdditionalField("streetAddress", changedPerson.getAdditionalField("streetAddress"));
                EmployeeAddressChangeEvent payload = new EmployeeAddressChangeEvent(existingPerson);
                ret.add(payload);
            }
        }
        return ret;
    }

    public EnumGender randomGender() {
        return RANDOM.nextBoolean() ? EnumGender.male : EnumGender.female;
    }

    public String randomGivenName(EnumGender gender) {
        return randomFromWeightedFile(getFileForGivenName(gender));
    }

    public String randomSurname() {
        return randomFromWeightedFile(getFileForSurname());
    }

    //- STATIC PUBLIC --------------------------------------------------------------------------------------------------

    public static String randomFromFile(String file) {
        ArrayList<String> valueList = RANDOM_FROM_FILE.get(file);
        if (valueList == null) {
            valueList = new ArrayList<>();
            Path path = FileSystems.getDefault().getPath(DATA_PATH + "/" + file);
            try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.trim().length() == 0 || line.startsWith("#")) continue;
                    valueList.add(line);
                }
            } catch (IOException io) {
                throw new IllegalStateException(io);
            }
            RANDOM_FROM_FILE.put(file, valueList);
            log.debug("Reading data file \"" + file + "\": nr of entries = " + valueList.size());
        }
        return valueList.get(RANDOM.nextInt(valueList.size()));
    }

    public static String randomFromWeightedFile(String file) {
        ArrayList<String> valueList = valueListFromWeightedFile(file);
        HashMap<Integer, Integer> weightMap = WEIGHT_FROM_WEIGHTED_FILE.get(file);
        return valueList.get(weightMap.get(RANDOM.nextInt(weightMap.size())));
    }

    public static ArrayList<String> valueListFromWeightedFile(String file) {
        ArrayList<String> valueList = RANDOM_FROM_WEIGHTED_FILE.get(file);
        if (valueList == null) {
            Instant start = Instant.now();
            valueList = new ArrayList<>();
            HashMap<Integer, Integer> weightMap = new HashMap<>();
            Path path = FileSystems.getDefault().getPath(DATA_PATH + "/" + file);
            try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.trim().length() == 0 || line.startsWith("#")) continue;
                    String[] pieces = line.split("[,]");
                    if (pieces.length == 2 && isNotNullOrEmpty(pieces[0]) && isNotNullOrEmpty(pieces[1])) {
                        int valuePos = valueList.size();
                        int weight = Integer.parseInt(pieces[1]);
                        valueList.add(pieces[0]);
                        for (int i = 0; i < weight; i++) {
                            weightMap.put(weightMap.size(), valuePos);
                        }
                    } else {
                        log.warn("Invalid line in " + file + ": " + line);
                    }
                }
            } catch (IOException io) {
                throw new IllegalStateException(io);
            }
            RANDOM_FROM_WEIGHTED_FILE.put(file, valueList);
            WEIGHT_FROM_WEIGHTED_FILE.put(file, weightMap);

            Instant finish = Instant.now();
            long timeElapsed = Duration.between(start, finish).toMillis();
            log.debug("Reading weighted data file \"" + file + "\": nr of entries = " + valueList.size()
                    + ", time to read = " + timeElapsed + " milliseconds");
        }

        return valueList;
    }

    //- PRIVATE --------------------------------------------------------------------------------------------------------

    private String getFileForGivenName(EnumGender gender) {
        return "givenname-" + ((EnumGender.male == gender) ? "male" : "female") + "-weighted-" + configuration.language + ".txt";
    }

    private String getFileForSurname() {
        return "surname-weighted-" + configuration.language + ".txt";
    }

    private static boolean isNotNullOrEmpty(String s) {
        return s != null && s.length() > 0;
    }
}
