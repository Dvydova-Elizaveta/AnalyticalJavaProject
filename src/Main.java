import test.DbTest;
import test.ParserTest;
import test.UlearnTest;

import java.io.IOException;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws Exception {
        //ParserTest.parserTest();
        //UlearnTest.ulearnTest();
        DbTest.dbTest();
    }
}