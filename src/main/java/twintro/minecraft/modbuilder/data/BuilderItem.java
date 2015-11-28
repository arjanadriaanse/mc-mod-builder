package twintro.minecraft.modbuilder.data;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class BuilderItem extends Item {
	
	public String model;

	public BuilderItem(String model) {
		this.model = model;
	}
}
