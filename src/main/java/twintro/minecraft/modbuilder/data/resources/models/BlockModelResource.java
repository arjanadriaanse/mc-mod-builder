package twintro.minecraft.modbuilder.data.resources.models;

import java.util.Map;

/**
 * Contains variabels for the block model.
 */
public class BlockModelResource {
	/**
	 * The parent block model. This is "cube_all" for blocks that use the same texture on all side.
	 * Blocks that have a different texture on all sides use "cube", with properties for the texture on each side.
	 * Blocks that are not a full block, like flowers or tall grass, use "cross".
	 */
	public String parent;
	/**
	 * The list of all textures used by the model.
	 * The first string is an indicator for where the texture needs to stand, like "up", "down", "all" or "cross", and the second is a reference to the file location.
	 */
	public Map<String, String> textures;
}
