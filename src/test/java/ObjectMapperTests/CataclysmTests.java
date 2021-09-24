package ObjectMapperTests;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.revature.annotations.Column;
import com.revature.annotations.Id;
import com.revature.cataclysm.Cataclysm;
import com.revature.objectMapper.ObjectSaver;
import com.revature.util.Configuration;
import com.revature.util.MetaModel;

public class CataclysmTests {
	static Cataclysm cataclysm;
	
	@BeforeClass
	public static void setup() throws NoSuchFieldException, SecurityException {
		
		TestConfig.config();
		cataclysm = new Cataclysm();
		System.out.println("SETUP");

	}

	@AfterClass
	public static void teardown() {
		
		cataclysm.dropTable(DummyModel.class);
//		System.out.println("TEARDOWN");
//		ConnectionFactory cf = ConnectionFactory.getInstance();
//		Connection conn = cf.getConnection();
//
//		String sql = "DROP TABLE IF EXISTS users;";
//		try {
//			Statement stmt = conn.createStatement();
//			stmt.execute(sql);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}

	}
	

	@Test
	public void testInsert_returnsNewPK() {
		
		DummyModel dm = new DummyModel("dummyUser", "password", 25, 185.5);
		assertEquals(1, cataclysm.insert(dm));
	}
	
	@Test
	public void testUpdate_returnsBoolean() {
		
	}
//	@Before
//	public void setup() {
//		cataclysm = new Cataclysm();
//		mockdao = mock(ObjectSaver.class);
//		cataclysm.om = mockdao;
//	}
//	
//	@After
//	public void teardown() {
//		cataclysm = null;
//		mockdao = null;
//	}
//	
//	@Test
//	public void testInsert_returnsNewPk() {
//		User u1 = new 
//	}
}
