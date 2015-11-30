package twintro.minecraft.modbuilder.data;

import net.minecraft.item.ItemFood;

public class BuilderItemFood extends ItemFood {

	public BuilderItemFood(int amount, boolean isWolfFood) {
		super(amount, isWolfFood);
	}

	public BuilderItemFood(int amount, float saturation, boolean isWolfFood) {
		super(amount, saturation, isWolfFood);
	}
}
