package twintro.minecraft.modbuilder.data.resources.models;

import java.util.HashMap;
import java.util.Map;

public class BlockstatesResources {
	public static final String dir = "assets/modbuilder/blockstates";
	
	public static Object regular(String model){
		Map<String, Map<String, String>> variants = new HashMap<String, Map<String, String>>();
		Map<String, String> normal = new HashMap<String, String>();
		normal.put("model", model);
		variants.put("normal", normal);
		return regular(variants);
	}
	
	public static Object regular(Map<String, Map<String, String>> variants){
		Map<String, Object> blockstates = new HashMap<String, Object>();
		blockstates.put("variants", variants);
		return blockstates;
	}
}
