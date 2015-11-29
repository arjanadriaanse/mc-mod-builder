package twintro.minecraft.modbuilder.data.resources;

public enum RecipeType {
	shaped(ShapedRecipe.class), shapeless(ShapelessRecipe.class), smelting(SmeltingRecipe.class);

	private Class value;

	private RecipeType(Class value) {
		this.value = value;
	}

	public Class getValue() {
		return (value);
	}
}
