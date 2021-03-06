package com.giraone.testdata.generator;

import com.giraone.testdata.fields.FieldEnhancer;
import com.giraone.testdata.output.ListWriter;
import com.giraone.testdata.output.ListWriterJson;

import java.io.File;
import java.util.HashMap;

public class GeneratorConfiguration {

    public boolean createFullEvents = false;

    /** the language for which the test data is generated (either "en" or "de") */
    public EnumLanguage language = EnumLanguage.de;

    /** the country for which the test data is generated - a ISO 3166-Alpha-3 code - currently only DEU is supported */
    public String country = "DEU";

    /** create also a sequence number (index) for each created item */
    public boolean withIndex = false;
    /** if withIndex is used, this is the start index */
    public int startIndex = 0;

    /** set of additional fields - {@see FieldEnhancer} */
    public HashMap<String, FieldEnhancer> additionalFields = new HashMap<>();

    /** the number of items, that should be produced in total or in a file */
    public int numberOfItems = 1;
    /** the number of files per directory */
    public int filesPerDirectory = -1;
    /** the number of directories to be created for splitting the output */
    public int numberOfDirectories = -1;

    /** the root directory into which the data is generated */
    public File rootDirectory = new File(".");

    /** the writer class responsible for the output serialization: typically either json or csv */
    public ListWriter listWriter = new ListWriterJson();
}
