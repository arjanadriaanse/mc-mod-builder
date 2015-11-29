package twintro.minecraft.modbuilder.data.resources;

import java.util.Set;

import net.minecraft.creativetab.CreativeTabs;
import twintro.minecraft.modbuilder.data.BuilderItem;
import twintro.minecraft.modbuilder.data.ResourceHelper;

public class ItemResource {
	public String model;
	public Set<String> tabs;

	public BuilderItem toItem() {
		CreativeTabs[] array = new CreativeTabs[tabs.size()];
		int i = 0;
		for (String tab : tabs)
			array[i++] = ResourceHelper.tabs.get(tab);
		BuilderItem item = new BuilderItem(model, array);
		return item;
	}
}
