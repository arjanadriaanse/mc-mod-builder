package twintro.minecraft.modbuilder.data.resources.items;

import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class ToolItemResource extends BaseItemResource {
	public String name; //onbelangrijk
	public Set<String> blocks; //blocks waarop dit item effect heeft
	public Float damage; //damage die je doet als je entities slaat
	public Integer durability; //hoe lang het meegaat
	public Integer harvestlevel; //hoe sterk het item is (0 is wood, 2=iron, etc)
	public Float efficiency; //hoe snel het blocks kapotmaakt
	public Integer enchantability; //hoe goed het te enchanten is
	public String repairitem; //naam van het item dat nodig is om de tool te repareren
	public String repairblock; //naam van het block dat nodig is om de tool te repareren

	public ToolItemResource() {
		type = ItemType.tool;
		stacksize = 1;
	}
}
