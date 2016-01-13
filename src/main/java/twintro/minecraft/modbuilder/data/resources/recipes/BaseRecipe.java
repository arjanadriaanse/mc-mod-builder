package twintro.minecraft.modbuilder.data.resources.recipes;

/**
 * Contains all basic properties for recipes.
 */
public abstract class BaseRecipe {
	/**
	 * The type of the recipe.
	 */
	public RecipeType type;
	/**
	 * The item that you gain when using the recipe.
	 */
	public ItemStackResource output;
}
