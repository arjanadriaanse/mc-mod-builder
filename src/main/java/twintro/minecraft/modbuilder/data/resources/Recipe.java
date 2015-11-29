package twintro.minecraft.modbuilder.data.resources;

import twintro.minecraft.modbuilder.data.Registerable;

public abstract class Recipe implements Registerable {
	public ItemStackResource output;
	public RecipeType type;

}
