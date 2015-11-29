package twintro.minecraft.modbuilder.data;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import twintro.minecraft.modbuilder.data.resources.Recipe;
import twintro.minecraft.modbuilder.data.resources.RecipeType;

public class ResourceDeserializer implements JsonDeserializer {

	@Override
	public Object deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		JsonObject obj = json.getAsJsonObject();
		if (typeOfT == Recipe.class) {
			RecipeType type = context.deserialize(obj.get("type"), RecipeType.class);
			return context.deserialize(json, type.getValue());
		} else
			return null;
	}

}
