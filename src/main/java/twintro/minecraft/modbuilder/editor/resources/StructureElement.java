package twintro.minecraft.modbuilder.editor.resources;

import java.io.File;
import java.io.FileReader;

import javax.swing.ImageIcon;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import twintro.minecraft.modbuilder.data.resources.ResourceDeserializer;
import twintro.minecraft.modbuilder.data.resources.recipes.BaseRecipe;
import twintro.minecraft.modbuilder.data.resources.structures.BaseStructureResource;
import twintro.minecraft.modbuilder.editor.generator.ResourcePackIO;

public class StructureElement extends Element {
	public BaseStructureResource structure;
	
	public static StructureElement getFromName(String name) throws Exception {
		StructureElement output = null;
		
		File itemFile = new File(ResourcePackIO.getURL(
				"assets/modbuilder/structures/" + name + ".json"));
		
		if (itemFile.exists()){
			ResourceDeserializer deserializer = new ResourceDeserializer();
			GsonBuilder builder = new GsonBuilder();
			builder.registerTypeAdapter(BaseStructureResource.class, deserializer);
			Gson gson = builder.create();
			
			BaseStructureResource structure = gson.fromJson(new FileReader(itemFile), BaseStructureResource.class);
			
			output = new StructureElement();
			output.name = name;
			output.structure = structure;
		}
		return output;
	}

	@Override
	public ImageIcon getImage() {
		String block = structure.block;
		if (block.startsWith("modbuilder:")){
			block = block.substring(11);
			try{
				return BlockElement.getFromName(block).getImage();
			} catch (Exception e){
				e.printStackTrace();
			}
		}
		return new ImageIcon("src/main/resources/icon.png");
	}
}
