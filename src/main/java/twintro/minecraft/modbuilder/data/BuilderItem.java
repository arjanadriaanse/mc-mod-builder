package twintro.minecraft.modbuilder.data;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class BuilderItem extends Item {
	private CreativeTabs[] tabs;

	public BuilderItem(CreativeTabs[] tabs) {
		this.tabs = tabs;
	}

	@Override
	public CreativeTabs[] getCreativeTabs() {
		return tabs != null ? tabs : super.getCreativeTabs();
	}
}
