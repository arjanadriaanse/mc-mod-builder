package twintro.minecraft.modbuilder.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.client.resources.data.IMetadataSerializer;

/**
 * Handles the deserialization of the additional property in resource pack
 * metadata.
 */
public class MetadataSerializer extends IMetadataSerializer {
	@Override
	public IMetadataSection parseMetadataSection(String property, JsonObject json) {
		Gson gson = new GsonBuilder().create();
		return gson.fromJson(json, MetadataSection.class);
	}
}
