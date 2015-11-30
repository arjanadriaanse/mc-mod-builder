package twintro.minecraft.modbuilder.data.resources;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class ResourceDeserializer implements JsonDeserializer {

	@Override
	public Object deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		JsonObject obj = json.getAsJsonObject();
		if (typeOfT == BaseItemResource.class) {
			ItemType type = (ItemType) (obj.has("type") ? context.deserialize(obj.get("type"), ItemType.class)
					: ItemType.regular);
			return context.deserialize(json, type.getValue());
		} else if (typeOfT == BaseRecipe.class) {
			RecipeType type = (RecipeType) (obj.has("type") ? context.deserialize(obj.get("type"), RecipeType.class)
					: RecipeType.shapeless);
			return context.deserialize(json, type.getValue());
		} else
			return null;
	}

}
