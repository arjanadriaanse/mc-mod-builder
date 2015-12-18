package twintro.minecraft.modbuilder.data.resources.items;

import java.util.List;
import java.util.Set;

public class FoodItemResource extends BaseItemResource {
	public int amount;
	public Float saturation;
	public Boolean wolf;
	public Boolean alwaysedible;
	public Set<Integer[]> effects; //van de vorm {PotionID (van 1-23), PotionDuration (in ticks), PotionAmplifier (0 = effectx1}

	public FoodItemResource() {
		type = ItemType.food;
	}
}
