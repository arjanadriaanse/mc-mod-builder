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
	public ShapedRecipeEditor(String nameNew, RecipesActivityPanel parent, Set<String> items, Set<String> blocks) {
		super(nameNew, parent, items, blocks);
		lblCreateTheShaped.setText("Create the recipe in the desired pattern");
		this.name = nameNew;
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
				if (indexChar != ' ' && indexChar != null) {
					buttons[i].item = shpdRcpy.input.get(indexChar);
					if (shpdRcpy.input.get(indexChar).item == "" || shpdRcpy.input.get(indexChar).item == null)
						buttons[i].setText(shpdRcpy.input.get(indexChar).block);
					else
						buttons[i].setText(shpdRcpy.input.get(indexChar).item);
				} 
			}
			buttons[9].item = shpdRcpy.output;
			if(buttons[9].item.amount != null && buttons[9].item.amount != 0){
				if (shpdRcpy.output.item == "" || shpdRcpy.output.item == null) 
					buttons[9].setText(shpdRcpy.output.amount + " "+shpdRcpy.output.block);
				else
					buttons[9].setText(shpdRcpy.output.amount + " "+shpdRcpy.output.item);
			} else {
				if (shpdRcpy.output.item == "" || shpdRcpy.output.item == null) 
					buttons[9].setText(shpdRcpy.output.block);
				else
					buttons[9].setText(shpdRcpy.output.item);
			}

			this.name = value;
		}


	protected void saveRecipe() {
		String[] recipe = {"","",""};
		
		Map<Character, ItemStackResource> recipeMap = new HashMap<Character, ItemStackResource>();
		int t =0;
		while (buttons[t].item.item == null && buttons[t].item.block == null){
			recipe[t/3] += " ";
			t++;
			if (t == 9){
				JOptionPane.showMessageDialog(this, "Please give atleast one item to have as input");
				return;
			}
		}
		if (buttons[9].item == null){
			JOptionPane.showMessageDialog(this, "Please give an output item");
			return;
		}
		recipeMap.put('a', buttons[t].item);
		recipe[t/3] += "a";	
		for (int i = t+1; i < 9; i++){
			String a = "";
			if (buttons[i].item.item == "" || buttons[i].item.item == null){
				a = buttons[i].item.block;
			}else{
				a = buttons[i].item.item;
			}
			if (a == "" || a == null) recipe[i/3] += " ";
			else {
				for(char b = (char) ('a'); b<='a'+i ; b++){
					if (a == recipeMap.get(b).item || a==recipeMap.get(b).block && recipeMap.get(b).container == buttons[i].item.container){
						recipe[i/3] += b;
						b+='j';
					}
					else if(!recipeMap.containsKey(b)) {
						recipeMap.put(b, buttons[i].item);
						recipe[i/3] += b;
					}
				}
			}
		}
		ItemStackResource outputItem = buttons[9].item;
		int outputAmount;
		RecipeElement savable = new RecipeElement();
		savable.name = this.name;
		ShapedRecipe shapedRecipe = new ShapedRecipe();
		List<String> recipeListForm = new ArrayList<String>();
		for (int i = 0; i < 3; i++)
			recipeListForm.add(recipe[i]);
		shapedRecipe.shape = recipeListForm;
		shapedRecipe.input = recipeMap; 
		shapedRecipe.output = outputItem;
		shapedRecipe.type = RecipeType.shaped;
		savable.recipe = shapedRecipe;
		main.addRecipe(savable);
	}
}
