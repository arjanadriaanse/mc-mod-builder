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
import javax.swing.JFrame;
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
import twintro.minecraft.modbuilder.editor.Editor;
import twintro.minecraft.modbuilder.editor.generator.LanguageFile;
import twintro.minecraft.modbuilder.editor.generator.ResourcePackIO;
import twintro.minecraft.modbuilder.editor.interfaces.choosewindows.ObjectRunnable;
import twintro.minecraft.modbuilder.editor.interfaces.editors.BlockEditor;
import twintro.minecraft.modbuilder.editor.resources.BlockElement;
import twintro.minecraft.modbuilder.editor.resources.ItemElement;
import twintro.minecraft.modbuilder.editor.resources.RecipeElement;

public class BlocksActivityPanel extends ObjectActivityPanel {
	private final ObjectRunnable runnable = new ObjectRunnable() {
		@Override
		public void run(Object obj) {
			saveBlock((BlockElement) obj);
		}
	};
	
	public BlocksActivityPanel(String header, String button) {
		super(header, button);
		openEditors = new HashMap<String, JFrame>();
	}

	@Override
	protected void add() {
		String name = nameDialog("Block");
		if (name == null) return;
		for (char c : new char[]{'"','#',':','_'}){
			if (name.contains(c + "")){
				int selected = JOptionPane.showConfirmDialog(this, "Your name cannot include the character '" + c + "'.", 
						"Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
				if (selected == JOptionPane.OK_OPTION)
					add();
				else
					return;
			}
		}
		name = name.replaceAll(" ", "_");
		if (!Editor.getItemList().containsKey(name)){
			BlockEditor editor = new BlockEditor(name, runnable, closeHandler);
			openEditors.put(name, editor);
		}
		else{
			int selected = JOptionPane.showConfirmDialog(this, "The name is already in use by another item.", 
					"Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
			if (selected == JOptionPane.OK_OPTION)
				add();
		}
	}
	
	@Override
	protected void edit() {
		String value = (String) list.getSelectedValue();
		try {
			if (!openEditors.containsKey(value)){
				BlockElement block = BlockElement.getFromName(value);
				openEditors.put(value, new BlockEditor(block, runnable, closeHandler));
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
		int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete " + value + "?\r\n"
				+ "References to this object will not be updated, which might cause problems.", 
				"Warning", JOptionPane.YES_NO_OPTION);
		if (result == JOptionPane.YES_OPTION){
			ResourcePackIO.deleteFile("assets/modbuilder/blocks/" + value + ".json");
			ResourcePackIO.deleteFile("assets/modbuilder/blockstates/" + value + ".json");
			ResourcePackIO.deleteFile("assets/modbuilder/models/block/" + value + ".json");
			ResourcePackIO.deleteFile("assets/modbuilder/models/item/" + value + ".json");
			removeElement(value);
			
			Editor.metaFile.resource.modbuilder.blocks.remove(value);
			Editor.metaFile.save();
			
			Editor.langFile.list.remove(LanguageFile.toLine(value, false));
			Editor.langFile.save();
		}
	}
	
	@Override
	public String updateList(){
		File folder = new File(ResourcePackIO.getURL("assets/modbuilder/blocks/"));
		if (folder.exists()){
			for (File file : folder.listFiles()){
				if (file.getAbsolutePath().endsWith(".json")){
					try {
						String name = file.getName().substring(0, file.getName().length() - 5);
						addElement(name, BlockElement.getFromName(name).getImage());
					} catch (Exception e) {
						e.printStackTrace();
						return "Could not find all block element objects for the block " + file.getName();
					}
				}
			}
		}
		return null;
	}
	
	private void saveBlock(BlockElement block){
		if (block.itemModel != null)
			ResourcePackIO.createFile(block.itemModel, "assets/modbuilder/models/item/" + block.name + ".json");
		if (block.blockModel != null)
			ResourcePackIO.createFile(block.blockModel, "assets/modbuilder/models/block/" + block.name + ".json");
		ResourcePackIO.createFile(block.blockstate, "assets/modbuilder/blockstates/" + block.name + ".json");
		ResourcePackIO.createFile(block.block, "assets/modbuilder/blocks/" + block.name + ".json");
		addElement(block.name, block.getImage());
		
		Editor.metaFile.resource.modbuilder.blocks.add(block.name);
		Editor.metaFile.save();
		
		Editor.langFile.list.add(LanguageFile.toLine(block.name, false));
		Editor.langFile.save();
	}
}
