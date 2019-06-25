package com.giraone.testdata.output;

import com.giraone.testdata.AbstractEmployeeCommand;
import com.giraone.testdata.Person;

import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

public interface ListWriter {
    void write(List<AbstractEmployeeCommand> personList, PrintStream out) throws IOException;
}
