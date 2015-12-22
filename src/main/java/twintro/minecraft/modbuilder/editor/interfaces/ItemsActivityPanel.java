package twintro.minecraft.modbuilder.editor.interfaces;
import java.util.ArrayList;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import twintro.minecraft.modbuilder.data.resources.ResourceDeserializer;
import twintro.minecraft.modbuilder.data.resources.blocks.BaseBlockResource;
import twintro.minecraft.modbuilder.data.resources.blocks.BlockResource;
import twintro.minecraft.modbuilder.data.resources.items.BaseItemResource;
import twintro.minecraft.modbuilder.data.resources.models.BlockModelResource;
import twintro.minecraft.modbuilder.data.resources.models.ItemModelResource;
import twintro.minecraft.modbuilder.editor.ActivityPanel;
import twintro.minecraft.modbuilder.editor.Editor;
import twintro.minecraft.modbuilder.editor.generator.ResourcePackGenerator;
import twintro.minecraft.modbuilder.editor.resources.BlockElement;
import twintro.minecraft.modbuilder.editor.resources.ItemElement;

public class ItemsActivityPanel extends ActivityPanel {

	private List<String> models;
	
	public ItemsActivityPanel(String header, String button, Editor main) {
		super(header, button, main);
		this.models = new ArrayList<String>();	
	}
	
	public ItemsActivityPanel(String header, String button, Editor main, ArrayList<String> models) {
		super(header, button, main);
		this.models = models;	
	}
	
	@Override
	protected void add() {
		String name = JOptionPane.showInputDialog("Item name:");
		if (name != null)
			if (name.replaceAll(" ", "").length() > 0)
				new ItemEditor(this ,models, name);
	}
	
	public void addItem(ItemElement item){
		createFile(item.itemModel, "assets/modbuilder/models/item/" + item.name + ".json");
		createFile(item.item, "assets/modbuilder/items/" + item.name + ".json");
		addElement(item.name, item.getImage());
		
		main.metaFile.resource.modbuilder.items.add(item.name);
		main.metaFile.save();
		
		main.langFile.list.add("item.modbuilder_" + item.name + ".name=" + item.name);
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
		// TODO AFTER MIKE DOES HIS SHIT
	}
	
	@Override
	protected void delete() {
		String value = (String) list.getSelectedValue();
		int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete " + value, 
				"Warning", JOptionPane.YES_NO_OPTION);
		if (result == JOptionPane.YES_OPTION){
			ResourcePackGenerator.deleteFile("assets/modbuilder/models/item/" + value + ".json");
			ResourcePackGenerator.deleteFile("assets/modbuilder/items/" + value + ".json");
			removeElement(value);
			main.metaFile.resource.modbuilder.items.remove(value);
			main.metaFile.save();
			main.langFile.list.remove("item.modbuilder_" + value + ".name=" + value);
			main.langFile.save();
		}
	}
	
	@Override
	public void updateList() {
		File folder = new File(ResourcePackGenerator.getURL("assets/modbuilder/items/"));
		if (folder.exists()){
			for (File file : folder.listFiles()){
				if (file.getAbsolutePath().endsWith(".json")){
					try {
						String name = file.getName().substring(0, file.getName().length() - 5);
						addElement(name, ItemElement.getFromName(name).getImage());
					} catch (Exception e) {
						System.out.println("Could not find all item element objects for " + file.getName());
					}
				}
			}
		}
	}
}
