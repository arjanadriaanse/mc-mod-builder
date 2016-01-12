package twintro.minecraft.modbuilder.editor.interfaces.activitypanels;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import twintro.minecraft.modbuilder.data.resources.ResourceDeserializer;
import twintro.minecraft.modbuilder.data.resources.blocks.BaseBlockResource;
import twintro.minecraft.modbuilder.data.resources.blocks.BlockResource;
import twintro.minecraft.modbuilder.data.resources.blocks.BlockType;
import twintro.minecraft.modbuilder.data.resources.items.ItemType;
import twintro.minecraft.modbuilder.data.resources.models.BlockModelResource;
import twintro.minecraft.modbuilder.editor.ActivityPanel;
import twintro.minecraft.modbuilder.editor.Editor;
import twintro.minecraft.modbuilder.editor.generator.ResourcePackGenerator;
import twintro.minecraft.modbuilder.editor.interfaces.editors.BlockEditor;
import twintro.minecraft.modbuilder.editor.resources.BlockElement;
import twintro.minecraft.modbuilder.editor.resources.ItemElement;
import twintro.minecraft.modbuilder.editor.resources.RecipeElement;

public class BlocksActivityPanel extends ActivityPanel {
	private Map<String,BlockEditor> openEditors;
	
	public BlocksActivityPanel(String header, String button, Editor main) {
		super(header, button, main);
		this.openEditors = new HashMap<String, BlockEditor>();
	}

	@Override
	protected void add() {
		String name = JOptionPane.showInputDialog("Block name:");
		if (name != null){
			if (name.replaceAll(" ", "").length() > 0 && !openEditors.containsKey(name)){
				BlockEditor editor = new BlockEditor(name, this);
				openEditors.put(name, editor);
			}
		}
		
	}
	
	public void updateTextureReferences(String old, String newName){
		try	{
			Set<String> names = this.getAllElements();
			for (String nameOfElement : names){
				BlockElement elementToReReference = BlockElement.getFromName(nameOfElement);
				boolean isChanged = false;
				if (elementToReReference.block.type == BlockType.regular){
					BlockModelResource elementClassed = (BlockModelResource)elementToReReference.blockModel; 
					for (String texture : elementClassed.textures.values()){
						if (texture == old){isChanged = true; texture = newName;}
					}
					if(isChanged)this.addBlock(elementToReReference);
				}
			}
		}catch(Exception e){}
	}
	
	public void addBlock(BlockElement block){
		if (block.itemModel != null)
			createFile(block.itemModel, "assets/modbuilder/models/item/" + block.name + ".json");
		if (block.blockModel != null)
			createFile(block.blockModel, "assets/modbuilder/models/block/" + block.name + ".json");
		createFile(block.blockstate, "assets/modbuilder/blockstates/" + block.name + ".json");
		createFile(block.block, "assets/modbuilder/blocks/" + block.name + ".json");
		addElement(block.name, block.getImage());
		
		main.metaFile.resource.modbuilder.blocks.add(block.name);
		main.metaFile.save();
		
		main.langFile.list.add("tile.modbuilder_" + block.name + ".name=" + block.name);
		main.langFile.save();
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
		try {
			if (!openEditors.containsKey(value)){
				BlockElement block = BlockElement.getFromName(value);
				openEditors.put(value, new BlockEditor(this, block));
			}
			else {
				openEditors.get(value).setVisible(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void delete() {
		String value = (String) list.getSelectedValue();
		int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete " + value, 
				"Warning", JOptionPane.YES_NO_OPTION);
		if (result == JOptionPane.YES_OPTION){
			ResourcePackGenerator.deleteFile("assets/modbuilder/blocks/" + value + ".json");
			ResourcePackGenerator.deleteFile("assets/modbuilder/blockstates/" + value + ".json");
			ResourcePackGenerator.deleteFile("assets/modbuilder/models/block/" + value + ".json");
			ResourcePackGenerator.deleteFile("assets/modbuilder/models/item/" + value + ".json");
			removeElement(value);
			main.metaFile.resource.modbuilder.blocks.remove(value);
			main.metaFile.save();
			main.langFile.list.remove("tile.modbuilder_" + value + ".name=" + value);
			main.langFile.save();
		}
	}
	
	@Override
	public void updateList() {
		File folder = new File(ResourcePackGenerator.getURL("assets/modbuilder/blocks/"));
		if (folder.exists()){
			for (File file : folder.listFiles()){
				if (file.getAbsolutePath().endsWith(".json")){
					try {
						String name = file.getName().substring(0, file.getName().length() - 5);
						addElement(name, BlockElement.getFromName(name).getImage());
					} catch (Exception e) {
						System.out.println("Could not find all block element objects for " + file.getName());
					}
				}
			}
		}
	}
	
	public void closeEditor(String name){
		if (openEditors.containsKey(name))
			openEditors.remove(name);
	}
}
