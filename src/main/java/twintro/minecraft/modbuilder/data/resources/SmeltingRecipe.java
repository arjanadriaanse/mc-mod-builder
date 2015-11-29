package twintro.minecraft.modbuilder.data.resources;

import net.minecraftforge.fml.common.registry.GameRegistry;

public class SmeltingRecipe extends Recipe {
	public ItemStackResource input;
	public float xp;

	public SmeltingRecipe() {
		type = RecipeType.smelting;
	}

	@Override
	public void register(String path) {
		GameRegistry.addSmelting(input.toItemStack(), output.toItemStack(), xp);
	}
}
