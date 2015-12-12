package twintro.minecraft.modbuilder.data.resources.models;

import java.util.HashMap;
import java.util.Map;

public class ItemModelResources {
	public static final String dir = "assets/modbuilder/models/item/";
	
	public static Object regularItem(String textureName){
		Map<String, double[]> thirdPerson = new HashMap<String, double[]>();
		thirdPerson.put("rotation", new double[]{ -90, 0, 0 });
		thirdPerson.put("translation", new double[]{ 0, 1, -3 });
		thirdPerson.put("scale", new double[]{ 0.55, 0.55, 0.55 });
		return item(textureName, thirdPerson);
	}
	
	public static Object toolItem(String textureName){
		Map<String, double[]> thirdPerson = new HashMap<String, double[]>();
		thirdPerson.put("rotation", new double[]{ 0, 90, -35 });
		thirdPerson.put("translation", new double[]{ 0, 1.25, -3.5 });
		thirdPerson.put("scale", new double[]{ 0.85, 0.85, 0.85 });
		return item(textureName, thirdPerson);
	}
	
	private static Object item(String textureName, Map<String, double[]> thirdPerson){
		Map<String, Object> model = new HashMap<String, Object>();
		
		String parent = "builtin/generated";
		model.put("parent", parent);
		
		Map<String, String> textures = new HashMap<String, String>();
		textures.put("layer0", "modbuilder:items/" + textureName);
		model.put("textures", textures);
		
		Map<String, Map<String, double[]>> display = new HashMap<String, Map<String, double[]>>();

		Map<String, double[]> firstPerson = new HashMap<String, double[]>();
		firstPerson.put("rotation", new double[]{ 0, -135, 25 });
		firstPerson.put("translation", new double[]{ 0, 4, 2 });
		firstPerson.put("scale", new double[]{ 1.7, 1.7, 1.7 });

		display.put("thirdperson", thirdPerson);
		display.put("firstperson", firstPerson);
		model.put("display", display);
		
		return model;
	}
	
	public static Object blockItem(String parentName){
		Map<String, Object> model = new HashMap<String, Object>();
		
		String parent = "modbuilder:block/" + parentName;
		model.put("parent", parent);
		
		Map<String, Map<String, double[]>> display = new HashMap<String, Map<String, double[]>>();

		Map<String, double[]> thirdPerson = new HashMap<String, double[]>();
		thirdPerson.put("rotation", new double[]{ 10, -45, 170 });
		thirdPerson.put("translation", new double[]{ 0, 1.5, -2.75 });
		thirdPerson.put("scale", new double[]{ 0.375, 0.375, 0.375 });

		display.put("thirdperson", thirdPerson);
		model.put("display", display);
		
		return model;
	}
}
