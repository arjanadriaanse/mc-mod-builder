package twintro.minecraft.modbuilder.data;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;

public class ItemResource extends Resource {
	public BuilderItem item;
	
	public String model;

	public ItemResource(String model) {
		this.model = model;
	}

	public BuilderItem toItem() {
		if (item == null)
			item = new BuilderItem(model);
		
		return item;
	}
}
