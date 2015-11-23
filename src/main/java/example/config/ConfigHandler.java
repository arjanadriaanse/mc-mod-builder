package example.config;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class ConfigHandler {
	private static final String CATEGORY_EXAMPLE = "example";
	
	private static String exampleTextValue;
	private static final String EXAMPLE_TEXT_KEY = "text";
	private static final String EXAMPLE_TEXT_DEFAULT = "default";
	
	public static void init(File file){
		Configuration config = new Configuration(file);
		config.load();
		exampleTextValue = config.get(CATEGORY_EXAMPLE, EXAMPLE_TEXT_KEY, EXAMPLE_TEXT_DEFAULT).getString();
		config.save();
	}
}
