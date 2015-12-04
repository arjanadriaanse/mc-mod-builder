package twintro.minecraft.modbuilder.data.resources;

import java.util.Set;

public class ItemResource extends BaseItemResource {
	public Set<TabResource> tabs;

	public ItemResource() {
		type = ItemType.regular;
	}
}
