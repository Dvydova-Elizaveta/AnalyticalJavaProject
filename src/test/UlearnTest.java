package test;
import parser.Parser;
import ulearn.Ulearn;

import java.io.IOException;

public class UlearnTest {
    public static void ulearnTest() throws IOException {
        var J = Parser.parseCSV("Data/java-rtf8.csv", "Java");
        Ulearn.addComments(J,"java-rtf");

        System.out.println(J.getName());
        for (String str: J.toListTasks()) {
            System.out.println(str);
        }
        System.out.println();

        var C = Parser.parseCSV("Data/basicprogramming_2.csv", "C#");
        Ulearn.addComments(C,"basicprogramming");

        System.out.println(C.getName());
        System.out.println();
        for (String str: C.toListTasks()) {
            System.out.println(str);
        }
    }
}
