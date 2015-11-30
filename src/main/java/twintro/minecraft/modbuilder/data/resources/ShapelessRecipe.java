package twintro.minecraft.modbuilder.data.resources;

import java.util.LinkedList;
import java.util.List;

public class ShapelessRecipe extends BaseRecipe {
	public ItemStackResource output;
	public List<ItemStackResource> input = new LinkedList<ItemStackResource>();

	public ShapelessRecipe() {
		type = RecipeType.shapeless;
	}
}
