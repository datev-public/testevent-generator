package com.giraone.testdata.output;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.giraone.testdata.AbstractEmployeeCommand;
import com.giraone.testdata.Person;

import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

public class ListWriterJson implements ListWriter {

    @Override
    public void write(List<Object> objectList, PrintStream out) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        //mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.writeValue(out, objectList);
    }
}
