package twintro.minecraft.modbuilder.data.resources.models;

import java.util.Map;

public class ItemModelResource {
	public String parent;
	public Map<String, String> textures;
	public Display display;
	
	public class Display {
		public Perspective thirdperson;
		public Perspective firstperson;
	}
	
	public class Perspective {
		public double[] rotation;
		public double[] translation;
		public double[] scale;
	}
}
