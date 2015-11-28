package twintro.minecraft.modbuilder.data;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class BuilderItem extends Item {

	public String model;

	private CreativeTabs[] tabs;

	public BuilderItem(String model, CreativeTabs[] tabs) {
		this.model = model;
		this.tabs = tabs;
	}

	@Override
	public CreativeTabs[] getCreativeTabs() {
		return tabs;
	}
}
