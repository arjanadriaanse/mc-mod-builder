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
		
		JsonArray recipes = json.getAsJsonArray("recipes");
		if (recipes != null) {
			for (int i = 0; i < recipes.size(); i++)
				res.recipes.add(recipes.get(i).getAsString());
		}
		
		JsonArray blocks = json.getAsJsonArray("blocks");
		if (blocks != null) {
			for (int i = 0; i < blocks.size(); i++)
				res.blocks.add(blocks.get(i).getAsString());
		}
		
		JsonArray items = json.getAsJsonArray("items");
		if (items != null) {
			for (int i = 0; i < items.size(); i++)
				res.items.add(items.get(i).getAsString());
		}
		
		return res;
	}
}
