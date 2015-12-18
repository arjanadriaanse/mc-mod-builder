package twintro.minecraft.modbuilder.editor.interfaces;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

import javax.swing.ImageIcon;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import twintro.minecraft.modbuilder.data.resources.ResourceDeserializer;
import twintro.minecraft.modbuilder.data.resources.blocks.BaseBlockResource;
import twintro.minecraft.modbuilder.data.resources.blocks.BlockResource;
import twintro.minecraft.modbuilder.data.resources.models.BlockModelResource;
import twintro.minecraft.modbuilder.editor.ActivityPanel;
import twintro.minecraft.modbuilder.editor.generator.ResourcePackGenerator;
import twintro.minecraft.modbuilder.editor.resources.BlockElement;

public class BlocksActivityPanel extends ActivityPanel {
	public BlocksActivityPanel(String header, String button) {
		super(header, button);
	}

	@Override
	protected void add() {
		
	}
	
	public void addBlock(BlockElement block){
		if (block.itemModel != null)
			createFile(block.itemModel, "assets/modbuilder/models/item/" + block.name + ".json");
		if (block.blockModel != null)
			createFile(block.blockModel, "assets/modbuilder/models/block/" + block.name + ".json");
		createFile(block.blockstate, "assets/modbuilder/blockstates/" + block.name + ".json");
		createFile(block.block, "assets/modbuilder/blocks/" + block.name + ".json");
		addElement(block.name, block.image);
	}
	
	public void createFile(Object model, String dir){
		try {
			ResourcePackGenerator.createFile(model, dir);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void edit() {
		String value = (String) list.getSelectedValue();
	}
	
	@Override
	protected void delete() {
		String value = (String) list.getSelectedValue();
	}
	
	@Override
	public void updateList() {
		File folder = new File(ResourcePackGenerator.getURL("assets/modbuilder/blocks/"));
		if (folder.exists()){
			ResourceDeserializer deserializer = new ResourceDeserializer();
			GsonBuilder builder = new GsonBuilder();
			builder.registerTypeAdapter(BaseBlockResource.class, deserializer);
			Gson gson = builder.create();
			for (File file : folder.listFiles()){
				try {
					addBlock(file, gson);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void addBlock(File file, Gson gson) throws Exception{
		if (file.getAbsolutePath().endsWith(".json")){
			ImageIcon img = new ImageIcon();
			String name = file.getName().substring(0, file.getName().length() - 5);
			BlockResource block = (BlockResource) 
					gson.fromJson(new FileReader(file), BaseBlockResource.class);
			if (block.model.startsWith("modbuilder:")){
				String modelName = block.model.substring(11);
				File model = new File(ResourcePackGenerator.getURL(
						"assets/modbuilder/models/block/" + modelName + ".json"));
				if (model.exists()){
					BlockModelResource blockModel = 
							gson.fromJson(new FileReader(model), BlockModelResource.class);
					String texture = null;
					if (blockModel.textures.containsKey("all"))
						texture = blockModel.textures.get("all");
					else{
						String[] textures = (String[]) blockModel.textures.values().toArray();
						if (textures.length > 0) texture = textures[0];
					}
					if (texture != null){
						if (texture.startsWith("modbuilder:")){
							img = new ImageIcon(ResourcePackGenerator.getURL(
									"assets/modbuilder/textures/" + texture.substring(11) + ".png"));
						}
					}
				}
			}
			addElement(name, img);
		}
	}
}
