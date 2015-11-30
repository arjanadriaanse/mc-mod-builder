package twintro.minecraft.modbuilder.data;

import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import net.minecraftforge.fml.common.registry.GameRegistry;
import twintro.minecraft.modbuilder.data.resources.BaseRecipe;
import twintro.minecraft.modbuilder.data.resources.ItemStackResource;
import twintro.minecraft.modbuilder.data.resources.ShapedRecipe;
import twintro.minecraft.modbuilder.data.resources.ShapelessRecipe;
import twintro.minecraft.modbuilder.data.resources.SmeltingRecipe;

public class RecipeRegistry {
	public static void register(BaseRecipe recipe) {
		if (recipe instanceof ShapelessRecipe)
			register((ShapelessRecipe) recipe);
		else if (recipe instanceof ShapedRecipe)
			register((ShapedRecipe) recipe);
		else if (recipe instanceof SmeltingRecipe)
			register((SmeltingRecipe) recipe);
	}

	public static void register(ShapelessRecipe recipe) {
		List params = new LinkedList();
		for (ItemStackResource stack : recipe.input) {
			params.add(ResourceConverter.toItemStack(stack));
		}
		GameRegistry.addShapelessRecipe(ResourceConverter.toItemStack(recipe.output), params.toArray());
	}

	public static void register(ShapedRecipe recipe) {
		List params = new LinkedList();
		params.addAll(recipe.shape);
		for (Entry<Character, ItemStackResource> entry : recipe.input.entrySet()) {
			params.add(entry.getKey());
			params.add(ResourceConverter.toItemStack(entry.getValue()));
		}
		GameRegistry.addShapedRecipe(ResourceConverter.toItemStack(recipe.output), params.toArray());
	}

	public static void register(SmeltingRecipe recipe) {
		GameRegistry.addSmelting(ResourceConverter.toItemStack(recipe.input),
				ResourceConverter.toItemStack(recipe.output), recipe.xp);
	}
}
