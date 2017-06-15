package jdbc;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by kkmike999 on 2017/06/12.
 */
public class SqlTest {
    Connection mConnection;

    @Before
    public void setUp() throws Exception {
        new File("build/db/").mkdirs();

        mConnection = DriverManager.getConnection("jdbc:sqlite:" + "build/db/test.dp");
        mConnection.setAutoCommit(false);
    }

    @Test
    public void testSelect() throws SQLException {
        Statement statement = mConnection.createStatement();

        statement.execute("CREATE TABLE UserModel (id integer AUTO_INCREMENT, name varchar,sex integer)");
        statement.execute("INSERT INTO UserModel ('name','sex') VALUES ('kk', 1)");

        String querySql = "SELECT * FROM 'UserModel'";

        ResultSet rs = statement.executeQuery(querySql);
        boolean   b  = rs.next();
        Assert.assertTrue(b);

        String s = rs.getString(2);

    }
}
