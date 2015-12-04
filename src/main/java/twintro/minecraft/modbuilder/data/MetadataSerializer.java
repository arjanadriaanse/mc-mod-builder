package twintro.minecraft.modbuilder.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.client.resources.data.IMetadataSerializer;
import twintro.minecraft.modbuilder.data.resources.meta.ModbuilderResource;

public class MetadataSerializer extends IMetadataSerializer {
	@Override
	public IMetadataSection parseMetadataSection(String property, JsonObject json) {
		if (!json.has(property))
			return null;

		Gson gson = new GsonBuilder().create();
		return new MetadataSection(gson.fromJson(json.getAsJsonObject(property), ModbuilderResource.class));
	}
}
