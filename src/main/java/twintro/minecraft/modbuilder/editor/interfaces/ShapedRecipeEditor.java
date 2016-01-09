package twintro.minecraft.modbuilder.editor.interfaces;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import twintro.minecraft.modbuilder.data.resources.recipes.ItemStackResource;
import twintro.minecraft.modbuilder.data.resources.recipes.ShapedRecipe;
import twintro.minecraft.modbuilder.data.resources.recipes.ShapelessRecipe;
import twintro.minecraft.modbuilder.editor.Editor;
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
	private String name;
	private RecipesActivityPanel main;
	private ItemStackButton[] buttons;
	


	/**
	 * Create the frame.
	 */
	public ShapedRecipeEditor(String nameNew, RecipesActivityPanel parent, Set<String> items, Set<String> blocks) {
		super(nameNew, parent, items, blocks);
	}


	public ShapedRecipeEditor(String value, RecipesActivityPanel recipesActivityPanel, RecipeElement recipe,
				Set<String> editorItems, Set<String> editorBlocks) {
		
			this(value, recipesActivityPanel, editorItems, editorBlocks);
			ShapedRecipe shpdRcpy = (ShapedRecipe)recipe.recipe;
			for (int i = 0; i < 10; i++){
				buttons[i].item = new ItemStackResource();
			}
			for(int i = 0; i < 9;i++){
				Character indexChar = (Character)shpdRcpy.shape.get((Integer)i/3).charAt(i%3);
				buttons[i].item = shpdRcpy.input.get(indexChar);
				if (shpdRcpy.input.get(indexChar).item == "" || shpdRcpy.input.get(indexChar).item == null)
					buttons[i].setText(shpdRcpy.input.get(indexChar).amount + " "+shpdRcpy.input.get(indexChar).block);
				else
					buttons[i].setText(shpdRcpy.input.get(indexChar).amount + " "+shpdRcpy.input.get(indexChar).item);
			}
			buttons[9].item = shpdRcpy.output;
			if (shpdRcpy.output.item == "" || shpdRcpy.output.item == null) 
				buttons[9].setText(shpdRcpy.output.amount + " "+shpdRcpy.output.block);
			else
				buttons[9].setText(shpdRcpy.output.amount + " "+shpdRcpy.output.item);

			this.name = value;
		}


	protected void saveRecipe() {
		String recipe = "";
		char c = 'a'+1;
		
		Map<Character, ItemStackResource> recipeMap = new HashMap<Character, ItemStackResource>();
		for (int i = 0; i < 9; i++){
			String a = buttons[i].getText();
			if (a == "") recipe += " ";
			else {
				for(char b = (char) ('a'+i); b<='j'; b++){
					if (a == recipeMap.get(b).item || a==recipeMap.get(b).block && recipeMap.get(b).container == buttons[i].item.container){
						recipe += b;
						b+='j';
					}
					else if(!recipeMap.containsKey(b)) {
						recipeMap.put(b, buttons[i].item);
						recipe += b;
					}
				}
			}
		}
		String outputItem = buttons[9].getText();
		int outputAmount = buttons[9].item.amount;
		//TODO send values to activity panel to save
	}
}
