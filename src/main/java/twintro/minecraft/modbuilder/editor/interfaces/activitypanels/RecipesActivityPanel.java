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
import twintro.minecraft.modbuilder.editor.Editor;
import twintro.minecraft.modbuilder.editor.generator.ResourcePackIO;
import twintro.minecraft.modbuilder.editor.interfaces.choosewindows.ObjectRunnable;
import twintro.minecraft.modbuilder.editor.interfaces.editors.ShapedRecipeEditor;
import twintro.minecraft.modbuilder.editor.interfaces.editors.ShapelessRecipeEditor;
import twintro.minecraft.modbuilder.editor.interfaces.editors.SmeltingRecipeEditor;
import twintro.minecraft.modbuilder.editor.interfaces.helperclasses.ActivityButton;
import twintro.minecraft.modbuilder.editor.resources.ItemElement;
import twintro.minecraft.modbuilder.editor.resources.RecipeElement;

public class RecipesActivityPanel extends ObjectActivityPanel {
	private final ObjectRunnable runnable = new ObjectRunnable() {
		@Override
		public void run(Object obj) {
			saveRecipe((RecipeElement) obj);
		}
	};
	
	public RecipesActivityPanel(String header, String button) {
		super(header, button);
		this.openEditors = new HashMap<String, JFrame>();
	}
	
	@Override
	protected void add() {
		String name = nameDialog("Recipe");
		if (name == null) return;
		ShapelessRecipeEditor editor = new ShapelessRecipeEditor(name, runnable, closeHandler);
		openEditors.put(name, editor);
	}
	
	private void addSmelting(){
		String name = nameDialog("Recipe");
		if (name == null) return;
		SmeltingRecipeEditor editor = new SmeltingRecipeEditor(name, runnable, closeHandler);
		openEditors.put(name, editor);
	}
	
	private void addShaped(){
		String name = nameDialog("Recipe");
		if (name == null) return;
		ShapedRecipeEditor editor = new ShapedRecipeEditor(name, runnable, closeHandler);
		openEditors.put(name, editor);
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
					editor = new ShapedRecipeEditor(recipe, runnable, closeHandler);
					break;
				case smelting:
					editor = new SmeltingRecipeEditor(recipe, runnable, closeHandler);
					break;
				default:
					editor = new ShapelessRecipeEditor(recipe, runnable, closeHandler);
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
			ResourcePackIO.deleteFile("assets/modbuilder/recipes/" + value + ".json");
			removeElement(value);
			
			Editor.metaFile.resource.modbuilder.recipes.remove(value);
			Editor.metaFile.save();
		}
	}
	
	@Override
	public void updateList() {
		File folder = new File(ResourcePackIO.getURL("assets/modbuilder/recipes/"));
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
	
	@Override
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
	
	private void saveRecipe(RecipeElement recipe){
		ResourcePackIO.createFile(recipe.recipe, "assets/modbuilder/recipes/" + recipe.name + ".json");
		ImageIcon img = recipe.getImage();
		if (img == null) img = new ImageIcon();
		addElement(recipe.name, img);
		
		Editor.metaFile.resource.modbuilder.recipes.add(recipe.name);
		Editor.metaFile.save();
	}
}
