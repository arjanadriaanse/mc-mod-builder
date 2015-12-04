package twintro.minecraft.modbuilder.data.resources.items;

import java.util.Set;

public class ToolItemResource extends BaseItemResource {
	public float damage;
	public String material;
	public Set<String> blocks;

	public ToolItemResource() {
		type = ItemType.tool;
	}
}
