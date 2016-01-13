package twintro.minecraft.modbuilder.editor.interfaces.activitypanels;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import twintro.minecraft.modbuilder.data.resources.ResourceDeserializer;
import twintro.minecraft.modbuilder.data.resources.blocks.BaseBlockResource;
import twintro.minecraft.modbuilder.data.resources.blocks.BlockResource;
import twintro.minecraft.modbuilder.data.resources.models.BlockModelResource;
import twintro.minecraft.modbuilder.editor.ActivityPanel;
import twintro.minecraft.modbuilder.editor.Editor;
import twintro.minecraft.modbuilder.editor.generator.ResourcePackGenerator;
import twintro.minecraft.modbuilder.editor.interfaces.editors.GroundStructureEditor;
import twintro.minecraft.modbuilder.editor.interfaces.editors.StructureEditor;
import twintro.minecraft.modbuilder.editor.resources.BlockElement;
import twintro.minecraft.modbuilder.editor.resources.ItemElement;


public class StructureActivityPanel  extends ActivityPanel {
	public Map<String,StructureEditor> openEditors;
	
	public StructureActivityPanel(String header, String button, Editor main) {
		super(header, button, main);
		this.openEditors = new HashMap<String, StructureEditor>();
	}

	@Override
	protected void add() {
		String name = JOptionPane.showInputDialog("Structure name:");
		if (name != null){
			if (name.replaceAll(" ", "").length() > 0 && !openEditors.containsKey(name)){
				StructureEditor editor = new GroundStructureEditor(name, this, main.getBlocksInBlockPanel());
				openEditors.put(name, editor);
			}
		}
		
	}
	
	public void addStructure(){
		/*
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
		*/
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
		int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete " + value, 
				"Warning", JOptionPane.YES_NO_OPTION);
		if (result == JOptionPane.YES_OPTION){
		/*	ResourcePackGenerator.deleteFile("assets/modbuilder/blocks/" + value + ".json");
			ResourcePackGenerator.deleteFile("assets/modbuilder/blockstates/" + value + ".json");
			ResourcePackGenerator.deleteFile("assets/modbuilder/models/block/" + value + ".json");
			ResourcePackGenerator.deleteFile("assets/modbuilder/models/item/" + value + ".json");
			*/
			removeElement(value);
			main.metaFile.resource.modbuilder.blocks.remove(value);
			main.metaFile.save();
			main.langFile.list.remove("tile.modbuilder_" + value + ".name=" + value);
			main.langFile.save();
		}
	}
	
	@Override
	public void updateList() {
		File folder = new File(ResourcePackGenerator.getURL("assets/modbuilder/structures/"));
		if (folder.exists()){
			for (File file : folder.listFiles()){
				if (file.getAbsolutePath().endsWith(".json")){
					try {
						String name = file.getName().substring(0, file.getName().length() - 5);
						addElement(name, BlockElement.getFromName(name).getImage());
					} catch (Exception e) {
						System.out.println("Could not find all structure element objects for " + file.getName());
					}
				}
			}
		}
	}
}
