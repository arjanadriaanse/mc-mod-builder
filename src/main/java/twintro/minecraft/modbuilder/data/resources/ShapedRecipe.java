package twintro.minecraft.modbuilder.data.resources;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ShapedRecipe extends BaseRecipe {
	public ItemStackResource output;
	public List<String> shape = new LinkedList<String>();
	public Map<Character, ItemStackResource> input = new LinkedHashMap<Character, ItemStackResource>();

	public ShapedRecipe() {
		type = RecipeType.shaped;
	}
}
