# A generator for event sourcing / CQRS events for persons

### Build

```
mvn package
```

## Run

### Simple usages of the command line

```
$ java -jar target/testevent-generator.jar
usage: java -jar testevent-generator.jar
 -h,--help                        print usage help
 -a,--additionalFields <arg>      comma separated list of additional fields
 -b,--startIndex                  if withIndex is used, this is the start index
 -d,--numberOfDirectories <arg>   the number of directories for splitting the output
 -f,--filesPerDirectory <arg>     the number of files per directory
 -l,--language <arg>              the language for which the test data is generated (either "en" or "de")
 -c,--country <arg>               the country for which the test data (postal addresses) is generated (currently only "DEU")
 -n,--numberOfItems <arg>         the number of items, that should be produced in total or in a file
 -p,--personId <arg>              type of additional person id: none, uuid, sequence
 -r,--rootDirectory               the root directory, where the output is written (default = .)
 -s,--serialize <arg>             the serialization mode: either json (default) or csv
 -w,--withIndex                   create also a sequence number (index) for each created item
```

### Open Issues

- ...

### Change Log

- Version 1.0.0 (25.06.2019)
- Version 1.1.0 (12.12.2021)
  - Dependency updates (because of log4j vulnerability) 