package test;

import parser.Parser;

public class ParserTest {
    public static void parserTest() {
        var C = Parser.parseCSV("Data/basicprogramming_2.csv", "C#");

        for (String str: C.toListStudents().subList(0, 10)) {
            System.out.println(str);
        }
        System.out.println();
        for (String str: C.toListTasks().subList(0, 10)) {
            System.out.println(str);
        }
        System.out.println();

        var J = Parser.parseCSV("Data/java-rtf8.csv", "Java");

        for (String str: J.toListStudents().subList(0, 10)) {
            System.out.println(str);
        }
        System.out.println();
        for (String str: J.toListTasks()) {
            System.out.println(str);
        }
    }
}
