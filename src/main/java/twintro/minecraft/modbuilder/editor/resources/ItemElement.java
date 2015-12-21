package twintro.minecraft.modbuilder.editor.resources;

import java.io.File;
import java.io.FileReader;
import java.util.Map;

import javax.swing.ImageIcon;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import twintro.minecraft.modbuilder.data.resources.ResourceDeserializer;
import twintro.minecraft.modbuilder.data.resources.blocks.BaseBlockResource;
import twintro.minecraft.modbuilder.data.resources.blocks.BlockResource;
import twintro.minecraft.modbuilder.data.resources.items.BaseItemResource;
import twintro.minecraft.modbuilder.data.resources.items.ItemResource;
import twintro.minecraft.modbuilder.data.resources.models.BlockModelResource;
import twintro.minecraft.modbuilder.data.resources.models.BlockstateResource;
import twintro.minecraft.modbuilder.data.resources.models.ItemModelResource;
import twintro.minecraft.modbuilder.data.resources.models.BlockstateResource.Variant;
import twintro.minecraft.modbuilder.editor.generator.ResourcePackGenerator;

public class ItemElement extends InventoryElement {
	public BaseItemResource item;
	
	public static ItemElement getFromName(String name) throws Exception {
		ItemElement output = null;
		
		File itemFile = new File(ResourcePackGenerator.getURL(
				"assets/modbuilder/items/" + name + ".json"));
		
		if (itemFile.exists()){
			ResourceDeserializer deserializer = new ResourceDeserializer();
			GsonBuilder builder = new GsonBuilder();
			builder.registerTypeAdapter(BaseItemResource.class, deserializer);
			Gson gson = builder.create();
			
			BaseItemResource item = gson.fromJson(new FileReader(itemFile), BaseItemResource.class);
			
			output = new ItemElement();
			output.name = name;
			output.item = item;
			
			if (item.model.startsWith("modbuilder:")){
				String itemModelName = item.model.substring(11);
				File itemModelFile = new File(ResourcePackGenerator.getURL(
						"assets/modbuilder/models/item/" + itemModelName + ".json"));
				if (itemModelFile.exists()){
					ItemModelResource itemModel = 
							gson.fromJson(new FileReader(itemModelFile), ItemModelResource.class);
					output.itemModel = itemModel;
				}
			}
		}
		return output;
	}
}
