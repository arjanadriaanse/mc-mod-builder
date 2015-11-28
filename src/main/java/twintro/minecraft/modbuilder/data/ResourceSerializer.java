package twintro.minecraft.modbuilder.data;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
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
			String tab = obj.get("tab").getAsString();
			return new BlockResource(material, model, tab);
		}
		else if (typeOfT == ItemResource.class) {
			String model = obj.get("model").getAsString();
			Set<String> tabs = new HashSet<String>();
			for (JsonElement tab : obj.get("tabs").getAsJsonArray())
				tabs.add(tab.getAsString());
			return new ItemResource(model, tabs);
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
			obj.addProperty("tab", block.tab);
		}
		else if (src instanceof ItemResource) {
			ItemResource item = (ItemResource)src;
			obj.addProperty("model", item.model);
			JsonArray tabs = new JsonArray();
			for (String tab : item.tabs)
				tabs.add(new JsonPrimitive(tab));
			obj.add("tabs", tabs);
		}
		else {
			return JsonNull.INSTANCE;
		}
		return obj;
	}

}
