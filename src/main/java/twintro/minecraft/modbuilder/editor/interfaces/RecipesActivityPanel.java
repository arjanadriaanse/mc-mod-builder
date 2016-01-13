package twintro.minecraft.modbuilder.editor.interfaces;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

import javax.swing.JOptionPane;

import twintro.minecraft.modbuilder.data.resources.recipes.ItemStackResource;
import twintro.minecraft.modbuilder.data.resources.recipes.RecipeType;
import twintro.minecraft.modbuilder.data.resources.recipes.ShapedRecipe;
import twintro.minecraft.modbuilder.data.resources.recipes.ShapelessRecipe;
import twintro.minecraft.modbuilder.data.resources.recipes.SmeltingRecipe;
import twintro.minecraft.modbuilder.editor.ActivityPanel;
import twintro.minecraft.modbuilder.editor.Editor;
import twintro.minecraft.modbuilder.editor.generator.ResourcePackGenerator;
import twintro.minecraft.modbuilder.editor.resources.ItemElement;
import twintro.minecraft.modbuilder.editor.resources.RecipeElement;

public class RecipesActivityPanel extends ActivityPanel {
	public Map<String, RecipeEditor> openEditors;
	
	public RecipesActivityPanel(String header, String button, Editor main) {
		super(header, button, main);
	}

	@Override
	protected void add() {
		
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
		addElement(recipe.name, recipe.getImage());
		
		main.metaFile.resource.modbuilder.recipes.add(recipe.name);
		main.metaFile.save();
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
			ResourcePackGenerator.deleteFile("assets/modbuilder/recipes/" + value + ".json");
			removeElement(value);
			main.metaFile.resource.modbuilder.recipes.remove(value);
			main.metaFile.save();
		}
	}
	
	@Override
	public void updateList() {
		File folder = new File(ResourcePackGenerator.getURL("assets/modbuilder/recipes/"));
		if (folder.exists()){
			for (File file : folder.listFiles()){
				if (file.getAbsolutePath().endsWith(".json")){
					try {
						String name = file.getName().substring(0, file.getName().length() - 5);
						addElement(name, RecipeElement.getFromName(name).getImage());
					} catch (Exception e) {
						System.out.println("Could not find all recipe element objects for " + file.getName());
					}
				}
			}
		}
	}
}
