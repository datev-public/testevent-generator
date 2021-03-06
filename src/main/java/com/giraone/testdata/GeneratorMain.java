package com.giraone.testdata;

import com.giraone.testdata.fields.FieldEnhancer;
import com.giraone.testdata.generator.EnumIdType;
import com.giraone.testdata.generator.EnumLanguage;
import com.giraone.testdata.generator.Generator;
import com.giraone.testdata.generator.GeneratorConfiguration;
import org.apache.commons.cli.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class GeneratorMain {

    private GeneratorConfiguration configuration = new GeneratorConfiguration();

    public static void main(String[] args) throws Exception {

        new GeneratorMain().run(args);
    }

    private void run(String[] args) throws Exception {

        Options options = new Options();
        options.addOption("h", "help", false, "print usage help");
        options.addOption("w", "createFullEvents", false, "create full events instead of commands (partial changes)");
        options.addOption("n", "numberOfItems", true, "the number of items, that should be produced in total or in a file");
        options.addOption("f", "filesPerDirectory", true, "the number of files per directory");
        options.addOption("d", "numberOfDirectories", true, "the number of directories for splitting the output");
        options.addOption("r", "rootDirectory", true, "the root directory, where the output is written (default = .)");


        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption("help")) {
                usage(options);
                System.exit(1);
            }
            configuration.createFullEvents = cmd.hasOption("createFullEvents");
            configuration.language = EnumLanguage.valueOf(cmd.getOptionValue("language", configuration.language.toString()));
            configuration.country = cmd.getOptionValue("country", configuration.country.toString());
            configuration.withIndex = cmd.hasOption("withIndex");
            configuration.startIndex = Integer.parseInt(cmd.getOptionValue("startIndex", "" + configuration.startIndex));
            parseFields(cmd.getOptionValue("additionalFields", "dateOfBirth,postalAddress,iban"), configuration.additionalFields);
            configuration.numberOfItems = Integer.parseInt(cmd.getOptionValue("numberOfItems", "" + configuration.numberOfItems));
            configuration.filesPerDirectory = Integer.parseInt(cmd.getOptionValue("filesPerDirectory", "" + configuration.filesPerDirectory));
            configuration.numberOfDirectories = Integer.parseInt(cmd.getOptionValue("numberOfDirectories", "" + configuration.numberOfDirectories));
            configuration.rootDirectory = new File(cmd.getOptionValue("rootDirectory", "."));
            run();

        } catch (ParseException e) {
            e.printStackTrace();
            usage(options);
        }
    }

    private void run() throws Exception {

        Generator generator = new Generator(configuration);

        if (configuration.filesPerDirectory > 0 || configuration.numberOfDirectories > 0) {
            runBlockwise(generator);
        } else {
            runOneList(generator, System.out);
        }
    }

    private void runOneList(Generator generator, PrintStream out) throws Exception {

        if (configuration.createFullEvents) {
            List<AbstractEmployeeEvent> eventList = generator.randomFullEvents(0, configuration.numberOfItems);
            configuration.listWriter.write(Collections.singletonList(eventList), out);
        } else {
            List<AbstractEmployeeCommand> commandList = generator.randomCommands(0, configuration.numberOfItems);
            configuration.listWriter.write(Collections.singletonList(commandList), out);
        }

    }

    private void runBlockwise(Generator generator) throws Exception {

        final String extension = "json";
        for (int directoryIndex = 0; directoryIndex < configuration.numberOfDirectories; directoryIndex++) {
            final String directoryName = String.format("d-%08d", directoryIndex);
            final File directory = new File(configuration.rootDirectory, directoryName);
            for (int fileIndex = 0; fileIndex < configuration.filesPerDirectory; fileIndex++) {
                if (fileIndex == 0) {
                    boolean done = directory.mkdirs();
                    if (!done) {
                        throw new IllegalStateException("Directory " + directory + " cannot be created!");
                    }
                }
                final String fileName = String.format("f-%08d.%s", fileIndex, extension);
                final File file = new File(directory, fileName);
                try (PrintStream out = new PrintStream(new FileOutputStream(file))) {
                    runOneList(generator, out);
                }
            }
        }
    }

    private void parseFields(String fieldCommaList, Map<String, FieldEnhancer> result) {

        for (String fieldName : fieldCommaList.trim().split(",")) {
            if (fieldName.length() < 2) continue;
            String className = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
            FieldEnhancer fieldEnhancer;
            try {
                fieldEnhancer = (FieldEnhancer) Class.forName("com.giraone.testdata.fields.FieldEnhancer" + className).newInstance();
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
            result.put(fieldName, fieldEnhancer);
        }
    }

    private void usage(Options options) {

        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("java -jar testdata-generator-1.0.jar", options);
    }
}
