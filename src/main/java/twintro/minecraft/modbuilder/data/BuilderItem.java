package twintro.minecraft.modbuilder.data;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

/**
 * Base class for new regular {@link Item}s.
 */
public class BuilderItem extends Item {
	private CreativeTabs[] tabs;

	/**
	 * @param tabs
	 * 		-the creative tabs the item has to be in.
	 */
	public BuilderItem(CreativeTabs[] tabs) {
		this.tabs = tabs;
	}

	@Override
	public CreativeTabs[] getCreativeTabs() {
		return tabs != null ? tabs : super.getCreativeTabs();
	}
}
