package test;

import db.DbRepository;
import parser.Parser;
import ulearn.Ulearn;

import java.sql.SQLException;

public class DbTest {
    public static void dbTest() throws Exception {
        /*var J = Parser.parseCSV("Data/java-rtf8.csv", "Java");
        Ulearn.addComments(J,"java-rtf");

        var C = Parser.parseCSV("Data/basicprogramming_2.csv", "C#");
        Ulearn.addComments(C,"basicprogramming");

        var dbOrm = new DbRepository();
        dbOrm.connect();
        dbOrm.createTable();
        dbOrm.saveTasks(J);
        dbOrm.saveTasks(C);
        dbOrm.clouse();*/
        var dbOrm = new DbRepository();
        dbOrm.connect();

        dbOrm.clouse();
    }

}
