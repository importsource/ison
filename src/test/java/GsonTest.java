

import java.sql.Connection;

import org.junit.Test;

import com.google.gson.Gson;
import com.importsource.dbcp.client.DBClient;
import com.importsource.dbcp.core.DbcpConnection;

/**
 * Unit test for simple App.
 */
public class GsonTest {
	@Test
	public void testDome4j() {
		Gson ison = new Gson();
		Connection conn=DbcpConnection.getConnection();
		System.out.println(ison.toJson(DBClient.list(conn, "select * from util_audit_log where 1=?","1")));
	}
}

