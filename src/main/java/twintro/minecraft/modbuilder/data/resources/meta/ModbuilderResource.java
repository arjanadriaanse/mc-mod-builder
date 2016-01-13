package twintro.minecraft.modbuilder.data.resources.meta;

import java.util.Set;

/**
 * Contains information on the resourcepack for the buildermod.
 */
public class ModbuilderResource {
	/**
	 * The ID of this specific mod.
	 */
	public String id;
	/**
	 * The names of all blocks that have to be loaded.
	 */
	public Set<String> blocks;
	/**
	 * The names of all items that have to be loaded.
	 */
	public Set<String> items;
	/**
	 * The names of all recipes that have to be loaded.
	 */
	public Set<String> recipes;
	/**
	 * The names of all structures that have to be loaded.
	 */
	public Set<String> structures;
}
