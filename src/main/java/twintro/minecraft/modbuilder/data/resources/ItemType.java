package twintro.minecraft.modbuilder.data.resources;

public enum ItemType {
	regular(ItemResource.class), food(FoodItemResource.class), tool(ToolItemResource.class);

	private Class value;

	private ItemType(Class value) {
		this.value = value;
	}

	public Class getValue() {
		return (value);
	}
}
