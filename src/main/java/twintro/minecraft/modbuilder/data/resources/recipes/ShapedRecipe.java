package twintro.minecraft.modbuilder.data.resources.recipes;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Contains methods for shaped recipes
 */
public class ShapedRecipe extends BaseRecipe {
	/**
	 * The shape of the recipe. This contains 3 strings of length 3, for example "aba","c c","aba".
	 * The first string is the top row of the recipe, the the second string is the middle row and the third string is the bottom row.
	 * In the variable "input" is said which character means with itemstack.
	 */
	public List<String> shape = new LinkedList<String>();
	/**
	 * Encodes itemstacks to characters, so that the "shape" variable can use these characters in defining the shape of the recipe.
	 */
	public Map<Character, ItemStackResource> input = new LinkedHashMap<Character, ItemStackResource>();

	public ShapedRecipe() {
		type = RecipeType.shaped;
	}
}
