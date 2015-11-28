package twintro.minecraft.modbuilder.data;

import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemResource extends Resource {
	public BuilderItem item;
	
	public String model;
	public Set<String> tabs;
	
	public ItemResource(String model, Set<String> tabs) {
		this.model = model;
		this.tabs = tabs;
	}

	public BuilderItem toItem() {
		if (item == null) {
			CreativeTabs[] array = new CreativeTabs[tabs.size()];
			int i = 0;
			for (String tab : tabs)
				array[i++] = ResourceHelper.tabs.get(tab);
			item = new BuilderItem(model, array);
		}
		return item;
	}
}
