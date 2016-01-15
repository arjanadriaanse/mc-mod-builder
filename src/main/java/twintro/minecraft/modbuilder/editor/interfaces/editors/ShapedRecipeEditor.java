package twintro.minecraft.modbuilder.editor.interfaces.editors;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import twintro.minecraft.modbuilder.data.resources.recipes.ItemStackResource;
import twintro.minecraft.modbuilder.data.resources.recipes.RecipeType;
import twintro.minecraft.modbuilder.data.resources.recipes.ShapedRecipe;
import twintro.minecraft.modbuilder.data.resources.recipes.ShapelessRecipe;
import twintro.minecraft.modbuilder.editor.Editor;
import twintro.minecraft.modbuilder.editor.interfaces.activitypanels.RecipesActivityPanel;
import twintro.minecraft.modbuilder.editor.resources.RecipeElement;

import java.awt.GridLayout;
import java.awt.Window;

import javax.swing.JButton;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import javax.swing.SwingConstants;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.image.ReplicateScaleFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;

public class ShapedRecipeEditor extends ShapelessRecipeEditor {
	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public ShapedRecipeEditor(String nameNew, RecipesActivityPanel parent) {
		super(nameNew, parent);
		lblCreateTheShaped.setText("Create the recipe in the desired pattern");
		this.name = nameNew;
	}

	public ShapedRecipeEditor(String value, RecipesActivityPanel recipesActivityPanel, RecipeElement recipe) {
		this(value, recipesActivityPanel);
		ShapedRecipe shpdRcpy = (ShapedRecipe)recipe.recipe;
		
		for(int i = 0; i < 9;i++){
			Character indexChar = (Character)shpdRcpy.shape.get((Integer)i/3).charAt(i%3);
			if (indexChar != ' ' && indexChar != null)
				buttons[i].chooseItem(shpdRcpy.input.get(indexChar));
		}
		buttons[9].chooseItem(shpdRcpy.output);
		
		this.name = value;
		
		changed = false;
	}
	
	@Override
	public boolean save() {
		String[] pattern = {"", "", ""};
		Map<Character, ItemStackResource> ingredients = new HashMap<Character, ItemStackResource>();
		for (int i = 0; i < 9; i++){
			ItemStackResource item = buttons[i].item;
			if (item == null || ((item.item == null || item.item == "") && (item.block == null || item.block == "")))
				pattern[i/3] += " ";
			else{
				for (char c = 'a'; true; c++){
					if (ingredients.containsKey(c)){
						ItemStackResource ingredient = ingredients.get(c);
						if (((ingredient.item != null && ingredient.item == item.item) 
								|| (ingredient.block != null && ingredient.block == item.block)) && ingredient.container == item.container){
							pattern[i/3] += c;
							break;
						}
					}
					else{
						pattern[i/3] += c;
						ingredients.put(c, item);
						break;
					}
				}
			}
		}
		if (ingredients.isEmpty()){
			int selected = JOptionPane.showConfirmDialog(this, "Please give atleast one item to have as input", 
					"Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
			if (selected == JOptionPane.OK_OPTION)
				return false;
			return true;
		}
		ItemStackResource item = buttons[9].item;
		if (item == null || ((item.item == null || item.item == "") && (item.block == null || item.block == ""))){
			int selected = JOptionPane.showConfirmDialog(this, "Not all required properties have been given a value yet.", 
					"Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
			if (selected == JOptionPane.OK_OPTION)
				return false;
			return true;
		}
		
		ItemStackResource outputItem = buttons[9].item;
		int outputAmount;
		RecipeElement savable = new RecipeElement();
		savable.name = this.name;
		ShapedRecipe shapedRecipe = new ShapedRecipe();
		List<String> recipeListForm = new ArrayList<String>();
		for (int i = 0; i < 3; i++)
			recipeListForm.add(pattern[i]);
		shapedRecipe.shape = recipeListForm;
		shapedRecipe.input = ingredients; 
		shapedRecipe.output = outputItem;
		shapedRecipe.type = RecipeType.shaped;
		savable.recipe = shapedRecipe;
		main.addRecipe(savable);
		
		dispose();
		
		return true;
	}
}
