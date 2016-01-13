package twintro.minecraft.modbuilder.editor.interfaces.activitypanels;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import net.minecraft.item.Item;
import twintro.minecraft.modbuilder.data.resources.blocks.BlockType;
import twintro.minecraft.modbuilder.data.resources.items.ItemResource;
import twintro.minecraft.modbuilder.data.resources.items.ItemType;
import twintro.minecraft.modbuilder.data.resources.models.BlockModelResource;
import twintro.minecraft.modbuilder.data.resources.models.ItemModelResource;
import twintro.minecraft.modbuilder.editor.ActivityButton;
import twintro.minecraft.modbuilder.editor.ActivityPanel;
import twintro.minecraft.modbuilder.editor.Editor;
import twintro.minecraft.modbuilder.editor.generator.ResourcePackGenerator;
import twintro.minecraft.modbuilder.editor.interfaces.editors.FoodItemEditor;
import twintro.minecraft.modbuilder.editor.interfaces.editors.RegularItemEditor;
import twintro.minecraft.modbuilder.editor.interfaces.editors.ToolItemEditor;
import twintro.minecraft.modbuilder.editor.resources.BlockElement;
import twintro.minecraft.modbuilder.editor.resources.ItemElement;

public class ItemsActivityPanel extends ActivityPanel {
	private List<String> models;
	private Map<String,RegularItemEditor> openEditors;
	
	public ItemsActivityPanel(String header, String button, Editor main) {
		super(header, button, main);
		this.models = new ArrayList<String>();
		this.openEditors = new HashMap<String,RegularItemEditor>();
	}
	
	public ItemsActivityPanel(String header, String button, Editor main, ArrayList<String> models) {
		super(header, button, main);
		this.models = models;
		this.openEditors = new HashMap<String,RegularItemEditor>();
	}
	
	@Override
	protected void add() {
		String name = JOptionPane.showInputDialog("Item name:");
		if (name != null){
			if (name.replaceAll(" ", "").length() > 0 && !openEditors.containsKey(name)){
				RegularItemEditor editor = new RegularItemEditor(name, this);
				openEditors.put(name, editor);
			}
		}
	}
	
	protected void addFood(){
		String name = JOptionPane.showInputDialog("Item name:");
		if (name != null){
			if (name.replaceAll(" ", "").length() > 0 && !openEditors.containsKey(name)){
				FoodItemEditor editor = new FoodItemEditor(name, this);
				openEditors.put(name, editor);
			}
		}
	}
	
	protected void addTool(){
		String name = JOptionPane.showInputDialog("Item name:");
		if (name != null){
			if (name.replaceAll(" ", "").length() > 0 && !openEditors.containsKey(name)){
				RegularItemEditor editor = new ToolItemEditor(name, this);
				openEditors.put(name, editor);
			}
		}
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
		try {
			if (!openEditors.containsKey(value)){
				ItemElement item = ItemElement.getFromName(value);
				ItemType type = item.item.type;
				RegularItemEditor editor;
				if (type == ItemType.food) editor = new FoodItemEditor(this, item);
				else if (type == ItemType.tool) editor = new ToolItemEditor(this, item);
				else editor = new RegularItemEditor(this, item);
				openEditors.put(value,editor);
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
			ResourcePackGenerator.deleteFile("assets/modbuilder/models/item/" + value + ".json");
			ResourcePackGenerator.deleteFile("assets/modbuilder/items/" + value + ".json");
			removeElement(value);
			main.metaFile.resource.modbuilder.items.remove(value);
			main.metaFile.save();
			main.langFile.list.remove("item.modbuilder_" + value + ".name=" + value);
			main.langFile.save();
		}
	}
	public void updateTextureReferences(String old, String newName){
		try	{
			Set<String> names = this.getAllElements();
			for (String nameOfElement : names){
				ItemElement elementToReReference = ItemElement.getFromName(nameOfElement);
				boolean isChanged = false;
				if (elementToReReference.item.type == ItemType.regular){
					ItemModelResource elementClassed = (ItemModelResource)elementToReReference.itemModel;
					for (String texture : elementClassed.textures.values()){
						if (texture == old){isChanged = true; texture = newName;}
					}
					if(isChanged)this.addItem(elementToReReference);
				}
				
			}
		}catch(Exception e){}
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
						e.printStackTrace();
						System.out.println("Could not find all item element objects for " + file.getName());
					}
				}
			}
		}
	}
	
	public void closeEditor(String name){
		if (openEditors.containsKey(name))
			openEditors.remove(name);
	}
	
	@Override
	protected void createButtonPanel(JPanel buttonPanel, String button) {
		JButton toolButton = new ActivityButton("New tool");
		toolButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addTool();
			}
		});
		buttonPanel.add(toolButton);
		
		JButton foodButton = new ActivityButton("New food");
		foodButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addFood();
			}
		});
		buttonPanel.add(foodButton);
		
		super.createButtonPanel(buttonPanel, button);
	}
}
