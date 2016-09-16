

import java.sql.Connection;

import org.junit.Test;

import com.importsource.dbcp.client.DBClient;
import com.importsource.dbcp.core.DbcpConnection;
import com.importsource.ison.Ison;

/**
 * Unit test for simple App.
 */
public class IsonTest {
	@Test
	public void testDome4j() {
		Ison ison = new Ison();
		Connection conn=DbcpConnection.getConnection();
		System.out.println(ison.toJson(DBClient.list(conn, "select * from util_audit_log where 1=?","1")));
	}
}

