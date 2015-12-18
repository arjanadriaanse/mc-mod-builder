package twintro.minecraft.modbuilder.data.resources.models;

import java.util.Map;

public class BlockstateResource {
	public Map<String, Variant> variants;
	
	public class Variant {
		public String model;
	}
}