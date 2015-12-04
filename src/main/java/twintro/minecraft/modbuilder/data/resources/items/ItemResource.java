package twintro.minecraft.modbuilder.data.resources.items;

import java.util.Set;

import twintro.minecraft.modbuilder.data.resources.TabResource;

public class ItemResource extends BaseItemResource {
	public Set<TabResource> tabs;

	public ItemResource() {
		type = ItemType.regular;
	}
}
