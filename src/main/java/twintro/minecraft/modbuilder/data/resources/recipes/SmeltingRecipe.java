package twintro.minecraft.modbuilder.data.resources.recipes;

/**
 * Contains methods for smelting recipes
 */
public class SmeltingRecipe extends BaseRecipe {
	/**
	 * The itemstack required as an input
	 */
	public ItemStackResource input;
	/**
	 * The amount of xp gained when finishing this smelting recipe.
	 */
	public float xp;

	public SmeltingRecipe() {
		type = RecipeType.smelting;
	}
}
