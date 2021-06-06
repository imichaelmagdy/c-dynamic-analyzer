# C Dynamic Analyzer

C Dynamic Analyzer is a tool to monitor the execution of statements written inside a C code, after entering the input arguments in the runtime.

## Dependencies

* Java SE Development Kit 14 - [JDK14](https://www.oracle.com/java/technologies/javase/jdk14-archive-downloads.html) (or higher)
* ANTLR 4.9.2 - [ANTLR4](https://www.antlr.org/download.html) (or higher)
* MinGW GCC Compiler - [MinGW-w64](http://mingw-w64.org/doku.php)

## Usage

To use the analyzer, put your C source code file in the `/test-cases` directory then run the `Analyzer.java` from the `/src` directory.

The Java program will ask for two lines as user input:
* **File name**: here you will enter the C source code file ***without*** the .c extension.
* **Arguments**: here you will enter all the ***space separated*** arguments of the code.

Example:

```Java
File name: prime
Arguments: 13
```

## Output

A HTML file in the `/test-cases` directory having the same name as the C source code file, containing:
* The name of the C source code file.
* The input arguments.
* The executed lines **highlighted in green** and the non-executed ones highlighted in red.
* The actual output of the C source code passing that specific arguments.
