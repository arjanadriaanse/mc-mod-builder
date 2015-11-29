package twintro.minecraft.modbuilder.data.resources;

import java.util.LinkedList;
import java.util.List;

import net.minecraftforge.fml.common.registry.GameRegistry;

public class ShapelessRecipe extends Recipe {
	public List<ItemStackResource> input = new LinkedList<ItemStackResource>();

	public ShapelessRecipe() {
		type = RecipeType.shapeless;
	}

	@Override
	public void register(String path) {
		List params = new LinkedList();
		for (ItemStackResource resource : input) {
			params.add(resource.toItemStack());
		}
		GameRegistry.addShapelessRecipe(output.toItemStack(), params.toArray());
	}
}
