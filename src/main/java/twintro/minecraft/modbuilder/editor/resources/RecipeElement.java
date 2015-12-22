package twintro.minecraft.modbuilder.editor.resources;

import javax.swing.ImageIcon;

import twintro.minecraft.modbuilder.data.resources.recipes.BaseRecipe;

public class RecipeElement extends Element {
	BaseRecipe recipe;
	
	@Override
	public ImageIcon getImage() {
		String outputName = recipe.output.item;
		if (outputName == null) outputName = recipe.output.block;
		
		return null;
	}
}
