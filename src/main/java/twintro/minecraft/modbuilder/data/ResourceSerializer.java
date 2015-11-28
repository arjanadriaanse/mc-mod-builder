package twintro.minecraft.modbuilder.data;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import net.minecraft.block.material.Material;

public class ResourceSerializer implements JsonSerializer<Resource>, JsonDeserializer<Resource> {
	
	@Override
	public Resource deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		JsonObject obj = json.getAsJsonObject();
		if (typeOfT == BlockResource.class) {
			String model = obj.get("model").getAsString();
			String material = obj.get("material").getAsString();
			return new BlockResource(material, model);
		}
		else if (typeOfT == ItemResource.class) {
			String model = obj.get("model").getAsString();
			return new ItemResource(model);
		}
		else
			return null;
	}

	@Override
	public JsonElement serialize(Resource src, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject obj = new JsonObject();
		if (src instanceof BlockResource) {
			BlockResource block = (BlockResource)src;
			obj.addProperty("material", block.material);
			obj.addProperty("model", block.model);
		}
		else if (src instanceof ItemResource) {
			ItemResource item = (ItemResource)src;
			obj.addProperty("model", item.model);
		}
		else {
			return JsonNull.INSTANCE;
		}
		return obj;
	}

}
