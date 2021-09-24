package ObjectMapperTests;

import com.revature.util.Configuration;

public class TestConfig {

	public static void config() throws NoSuchFieldException, SecurityException {
		Configuration cfg = new Configuration();
		cfg.addAnnotatedClass(DummyModel.class);
		
		cfg.init();
	}
}
