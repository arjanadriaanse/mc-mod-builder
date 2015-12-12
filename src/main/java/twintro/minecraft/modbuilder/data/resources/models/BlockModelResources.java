package twintro.minecraft.modbuilder.data.resources.models;

import java.util.HashMap;
import java.util.Map;

public class BlockModelResources {
	public static final String dir = "assets/modbuilder/models/block/";
	
	public static Object regular(String textureName){
		Map<String, String> textures = new HashMap<String, String>();
		textures.put("all", "examplemod:blocks/" + textureName);
		return regular("block/cube_all", textures);
	}
	
	public static Object regular(String parent, Map<String, String> textures){
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("parent", parent);
		model.put("textures", textures);
		return model;
	}
}
