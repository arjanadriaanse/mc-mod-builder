package twintro.minecraft.modbuilder.data.resources;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import twintro.minecraft.modbuilder.data.resources.blocks.BaseBlockResource;
import twintro.minecraft.modbuilder.data.resources.blocks.BlockType;
import twintro.minecraft.modbuilder.data.resources.items.BaseItemResource;
import twintro.minecraft.modbuilder.data.resources.items.ItemType;
import twintro.minecraft.modbuilder.data.resources.recipes.BaseRecipe;
import twintro.minecraft.modbuilder.data.resources.recipes.RecipeType;
import twintro.minecraft.modbuilder.data.resources.structures.BaseStructureResource;
import twintro.minecraft.modbuilder.data.resources.structures.StructureType;

/**
 * Handles the deserialization of subtypes of abstract resources.
 */
public class ResourceDeserializer implements JsonDeserializer {

	@Override
	public Object deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		JsonObject obj = json.getAsJsonObject();
		if (typeOfT == BaseBlockResource.class) {
			BlockType type = (BlockType) (obj.has("type") ? context.deserialize(obj.get("type"), BlockType.class)
					: BlockType.regular);
			return context.deserialize(json, type.getValue());
		} else if (typeOfT == BaseItemResource.class) {
			ItemType type = (ItemType) (obj.has("type") ? context.deserialize(obj.get("type"), ItemType.class)
					: ItemType.regular);
			return context.deserialize(json, type.getValue());
		} else if (typeOfT == BaseStructureResource.class) {
			StructureType type = (StructureType) (obj.has("type") ? context.deserialize(obj.get("type"), StructureType.class)
					: StructureType.ore);
			return context.deserialize(json, type.getValue());
		} else if (typeOfT == BaseRecipe.class) {
			RecipeType type = (RecipeType) (obj.has("type") ? context.deserialize(obj.get("type"), RecipeType.class)
					: RecipeType.shapeless);
			return context.deserialize(json, type.getValue());
		} else
			return null;
	}

}
