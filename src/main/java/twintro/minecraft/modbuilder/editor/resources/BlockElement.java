package twintro.minecraft.modbuilder.editor.resources;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Map;

import javax.swing.ImageIcon;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import twintro.minecraft.modbuilder.data.resources.ResourceDeserializer;
import twintro.minecraft.modbuilder.data.resources.blocks.BaseBlockResource;
import twintro.minecraft.modbuilder.data.resources.blocks.BlockResource;
import twintro.minecraft.modbuilder.data.resources.items.BaseItemResource;
import twintro.minecraft.modbuilder.data.resources.models.BlockModelResource;
import twintro.minecraft.modbuilder.data.resources.models.BlockstateResource;
import twintro.minecraft.modbuilder.data.resources.models.ItemModelResource;
import twintro.minecraft.modbuilder.data.resources.models.BlockstateResource.Variant;
import twintro.minecraft.modbuilder.editor.generator.ResourcePackGenerator;

public class BlockElement extends InventoryElement {
	public BaseBlockResource block;
	public BlockstateResource blockstate;
	public BlockModelResource blockModel;
	
	public static BlockElement getFromName(String name) throws Exception {
		BlockElement output = null;
		
		File blockFile = new File(ResourcePackGenerator.getURL(
				"assets/modbuilder/blocks/" + name + ".json"));
		if (blockFile.exists()){
			ResourceDeserializer deserializer = new ResourceDeserializer();
			GsonBuilder builder = new GsonBuilder();
			builder.registerTypeAdapter(BaseBlockResource.class, deserializer);
			Gson gson = builder.create();
			
			BlockResource block = (BlockResource) 
					gson.fromJson(new FileReader(blockFile), BaseBlockResource.class);
			
			File blockstateFile = new File(ResourcePackGenerator.getURL(
					"assets/modbuilder/blockstates/" + name + ".json"));
			if (blockstateFile.exists()){
				BlockstateResource blockstate = 
						gson.fromJson(new FileReader(blockstateFile), BlockstateResource.class);
				
				output = new BlockElement();
				output.name = name;
				output.block = block;
				output.blockstate = blockstate;
				
				if (block.model.startsWith("modbuilder:")){
					String itemModelName = block.model.substring(11);
					File itemModelFile = new File(ResourcePackGenerator.getURL(
							"assets/modbuilder/models/item/" + itemModelName + ".json"));
					if (itemModelFile.exists()){
						ItemModelResource itemModel = 
								gson.fromJson(new FileReader(itemModelFile), ItemModelResource.class);
						output.itemModel = itemModel;
					}
				}
				
				String blockModelName = null;
				Map<String, Variant> blockstateVariants = blockstate.variants;
				if (blockstateVariants.containsKey("normal"))
					blockModelName = blockstateVariants.get("normal").model;
				else{
					Object[] variants = blockstateVariants.values().toArray();
					if (variants.length > 0) blockModelName = ((Variant) variants[0]).model;
				}
				
				if (blockModelName != null){
					if (blockModelName.startsWith("modbuilder:")){
						File blockModelFile = new File(ResourcePackGenerator.getURL(
								"assets/modbuilder/models/block/" + blockModelName.substring(11) + ".json"));
						if (blockModelFile.exists()){
							BlockModelResource blockModel = 
									gson.fromJson(new FileReader(blockModelFile), BlockModelResource.class);
							output.blockModel = blockModel;
						}
					}
				}
			}
		}
		return output;
	}
	
	@Override
	public ImageIcon getImage() {
		return getImage(blockModel.textures, "all");
	}
}
