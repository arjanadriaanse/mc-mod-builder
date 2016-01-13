package twintro.minecraft.modbuilder.data.resources.recipes;

import java.util.LinkedList;
import java.util.List;

/**
 * Contains methods for shapeless recipes
 */
public class ShapelessRecipe extends BaseRecipe {
	/**
	 * List of the itemstacks that are required as an input.
	 */
	public List<ItemStackResource> input = new LinkedList<ItemStackResource>();

	public ShapelessRecipe() {
		type = RecipeType.shapeless;
	}
}
