package twintro.minecraft.modbuilder.data.resources;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraftforge.fml.common.registry.GameRegistry;

public class ShapedRecipe extends Recipe {
	public Map<Character, ItemStackResource> input = new HashMap<Character, ItemStackResource>();
	public List<String> shape = new LinkedList<String>();

	public ShapedRecipe() {
		type = RecipeType.shaped;
	}

	@Override
	public void register(String path) {
		List params = new LinkedList();
		params.addAll(shape);
		for (Entry<Character, ItemStackResource> entry : input.entrySet()) {
			params.add(entry.getKey());
			params.add(entry.getValue().toItemStack());
		}
		GameRegistry.addShapedRecipe(output.toItemStack(), params.toArray());
	}
}
