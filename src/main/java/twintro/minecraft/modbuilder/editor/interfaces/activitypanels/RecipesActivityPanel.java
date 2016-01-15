package twintro.minecraft.modbuilder.editor.interfaces.activitypanels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import twintro.minecraft.modbuilder.data.resources.items.ItemType;
import twintro.minecraft.modbuilder.data.resources.recipes.ItemStackResource;
import twintro.minecraft.modbuilder.data.resources.recipes.RecipeType;
import twintro.minecraft.modbuilder.data.resources.recipes.ShapedRecipe;
import twintro.minecraft.modbuilder.data.resources.recipes.ShapelessRecipe;
import twintro.minecraft.modbuilder.data.resources.recipes.SmeltingRecipe;
import twintro.minecraft.modbuilder.editor.ActivityButton;
import twintro.minecraft.modbuilder.editor.ActivityPanel;
import twintro.minecraft.modbuilder.editor.Editor;
import twintro.minecraft.modbuilder.editor.generator.ResourcePackGenerator;
import twintro.minecraft.modbuilder.editor.interfaces.editors.ShapedRecipeEditor;
import twintro.minecraft.modbuilder.editor.interfaces.editors.ShapelessRecipeEditor;
import twintro.minecraft.modbuilder.editor.interfaces.editors.SmeltingRecipeEditor;
import twintro.minecraft.modbuilder.editor.resources.ItemElement;
import twintro.minecraft.modbuilder.editor.resources.RecipeElement;

public class RecipesActivityPanel extends ActivityPanel {
	public Map<String, JFrame> openEditors;
	
	public RecipesActivityPanel(String header, String button) {
		super(header, button);
		this.openEditors = new HashMap<String, JFrame>();
	}

	protected void createButtonPanel(JPanel buttonPanel, String button) {
		JButton ShapedButton = new ActivityButton("New Shaped Recipe");
		ShapedButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addShaped();
			}
		});
		buttonPanel.add(ShapedButton);
		
		super.createButtonPanel(buttonPanel, button);
		
		JButton shapedButton = new ActivityButton("New Smelting Recipe");
		shapedButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addSmelting();
			}
		});
		buttonPanel.add(shapedButton);
	}
	
	@Override
	protected void add() {
		String name = JOptionPane.showInputDialog("Recipe name:");
		if (name != null){
			if (name.replaceAll(" ", "").length() > 0 && !openEditors.containsKey(name)){
				ShapelessRecipeEditor editor = new ShapelessRecipeEditor(name, this);
				openEditors.put(name, editor);
			}
		}
	}
	
	protected void addSmelting(){
		String name = JOptionPane.showInputDialog("Recipe name:");
		if (name != null){
			if (name.replaceAll(" ", "").length() > 0 && !openEditors.containsKey(name)){
				SmeltingRecipeEditor editor = new SmeltingRecipeEditor(name, this);
				openEditors.put(name, editor);
			}
		}
	}
	
	protected void addShaped(){
		String name = JOptionPane.showInputDialog("Recipe name:");
		if (name != null){
			if (name.replaceAll(" ", "").length() > 0 && !openEditors.containsKey(name)){
				ShapedRecipeEditor editor = new ShapedRecipeEditor(name, this);
				openEditors.put(name, editor);
			}
		}
	}
	
	public void updateItemAndBlockReferences(String old, String newName, boolean isBlock){
		try	{
			Set<String> names = this.getAllElements();
			for (String nameOfElement : names){
				RecipeElement elementToReReference = RecipeElement.getFromName(nameOfElement);
				boolean isChanged = false;
				if (elementToReReference.recipe.type == RecipeType.shaped){
					ShapedRecipe elementClassed = (ShapedRecipe)elementToReReference.recipe;
					for (ItemStackResource item : elementClassed.input.values()){	
						if (isBlock && item.block == old) {item.block = newName; isChanged = true;}
						if (!isBlock && item.item == old) {item.item = newName; isChanged = true;}
					}
					if (isBlock && elementClassed.output.block == old){elementClassed.output.block = newName; isChanged=true;}
					if (!isBlock && elementClassed.output.item == old){elementClassed.output.item = newName; isChanged=true;}
					if (isChanged) this.addRecipe(elementToReReference);
				} else if (elementToReReference.recipe.type == RecipeType.shapeless){
					ShapelessRecipe elementClassed = (ShapelessRecipe)elementToReReference.recipe;
					for (ItemStackResource item : elementClassed.input){	
						if (isBlock && item.block == old) {item.block = newName; isChanged = true;}
						if (!isBlock && item.item == old) {item.item = newName; isChanged = true;}
					}
					if (isBlock && elementClassed.output.block == old){elementClassed.output.block = newName; isChanged=true;}
					if (!isBlock && elementClassed.output.item == old){elementClassed.output.item = newName; isChanged=true;}
					if (isChanged) this.addRecipe(elementToReReference);					
				} else if (elementToReReference.recipe.type == RecipeType.smelting){
					SmeltingRecipe elementClassed = (SmeltingRecipe)elementToReReference.recipe;
					if (isBlock && elementClassed.input.block == old){elementClassed.input.block = newName; isChanged=true;}
					if (!isBlock && elementClassed.input.item == old){elementClassed.input.item = newName; isChanged=true;}
					if (isBlock && elementClassed.output.block == old){elementClassed.output.block = newName; isChanged=true;}
					if (!isBlock && elementClassed.output.item == old){elementClassed.output.item = newName; isChanged=true;}
					if (isChanged) this.addRecipe(elementToReReference);					
				}
			}
		} catch (Exception e){}
	}
	
	public void addRecipe(RecipeElement recipe){
		createFile(recipe.recipe, "assets/modbuilder/recipes/" + recipe.name + ".json");
		ImageIcon img = recipe.getImage();
		if (img == null) img = new ImageIcon();
		addElement(recipe.name, img);
		
		Editor.metaFile.resource.modbuilder.recipes.add(recipe.name);
		Editor.metaFile.save();
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
				RecipeElement recipe = RecipeElement.getFromName(value);
				RecipeType type = recipe.recipe.type;
				JFrame editor;
				
				switch(type){
				case shaped:
					editor = new ShapedRecipeEditor(value, this, recipe);
					break;
				case smelting:
					editor = new SmeltingRecipeEditor(value, this, recipe);
					break;
				default:
					editor = new ShapelessRecipeEditor(value, this, recipe);
					break;
				}
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
		int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete " + value + "?\r\n"
				+ "References to this object will not be updated, which might cause problems.", 
				"Warning", JOptionPane.YES_NO_OPTION);
		if (result == JOptionPane.YES_OPTION){
			ResourcePackGenerator.deleteFile("assets/modbuilder/recipes/" + value + ".json");
			removeElement(value);
			
			Editor.metaFile.resource.modbuilder.recipes.remove(value);
			Editor.metaFile.save();
		}
	}
	
	public void closeEditor(String name) {
		openEditors.remove(name);
	}
	
	@Override
	public void updateList() {
		File folder = new File(ResourcePackGenerator.getURL("assets/modbuilder/recipes/"));
		if (folder.exists()){
			for (File file : folder.listFiles()){
				if (file.getAbsolutePath().endsWith(".json")){
					try {
						String name = file.getName().substring(0, file.getName().length() - 5);
						ImageIcon img = RecipeElement.getFromName(name).getImage();
						if (img == null) img = new ImageIcon();
						addElement(name, img);
					} catch (Exception e) {
						System.out.println("Could not find all recipe element objects for " + file.getName());
					}
				}
			}
		}
	}
}
