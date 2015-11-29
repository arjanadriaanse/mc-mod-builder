package twintro.minecraft.modbuilder.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.client.resources.data.IMetadataSerializer;

public class MetadataSerializer extends IMetadataSerializer {
	@Override
	public IMetadataSection parseMetadataSection(String property, JsonObject json) {
		if (!json.has(property))
			return null;

		json = json.getAsJsonObject(property);
		MetadataSection res = new MetadataSection();

		JsonArray items = json.getAsJsonArray("items");
		if (items != null) {
			for (JsonElement item : items)
				res.items.add(item.getAsString());
		}

		JsonArray blocks = json.getAsJsonArray("blocks");
		if (blocks != null) {
			for (JsonElement block : blocks)
				res.blocks.add(block.getAsString());
		}

		JsonArray recipes = json.getAsJsonArray("recipes");
		if (recipes != null) {
			for (JsonElement recipe : recipes)
				res.recipes.add(recipe.getAsString());
		}
		return res;
	}
}
