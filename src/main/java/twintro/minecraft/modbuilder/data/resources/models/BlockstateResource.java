package twintro.minecraft.modbuilder.data.resources.models;

import java.util.Map;

/**
 * Contains information on the textures on different places of the model.
 */
public class BlockstateResource {
	public Map<String, Variant> variants;
	
	public class Variant {
		public String model;
	}
}