package twintro.minecraft.modbuilder.data.resources.items;

public class FoodItemResource extends BaseItemResource {
	public int amount;
	public Float saturation;
	public Boolean wolf;

	public FoodItemResource() {
		type = ItemType.food;
	}
}
