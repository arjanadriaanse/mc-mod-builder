package twintro.minecraft.modbuilder.editor.interfaces;

import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;

import twintro.minecraft.modbuilder.editor.ActivityPanel;
import twintro.minecraft.modbuilder.editor.Editor;
import twintro.minecraft.modbuilder.editor.generator.ResourcePackGenerator;
import twintro.minecraft.modbuilder.editor.resources.ItemElement;
import twintro.minecraft.modbuilder.editor.resources.RecipeElement;

public class RecipesActivityPanel extends ActivityPanel {
	public RecipesActivityPanel(String header, String button, Editor main) {
		super(header, button, main);
	}

	@Override
	protected void add() {
		
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
