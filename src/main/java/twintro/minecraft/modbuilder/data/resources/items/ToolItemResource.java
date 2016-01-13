package twintro.minecraft.modbuilder.data.resources.items;

import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

/**
 * The extended item properties for tool items.
 */
public class ToolItemResource extends BaseItemResource {
	/**
	 * The names of all blocks this tool is effective on.
	 */
	public Set<String> blocks;
	/**
	 * The damage dealt to entities when hit by the tool.
	 */
	public Float damage;
	/**
	 * The durability of the item. Every time the item is used, it loses one durability.
	 * When the item has no durability left, it will be destroyed.
	 */
	public Integer durability;
	/**
	 * How strong the tool is. 0 is as strong as gold/wood, 1 is stone, 2 is iron and 3 is diamond.
	 * Every block also has a harvestlevel value, and if the tool has a value larger or equal to the value of the block, the tool is effective.
	 */
	public Integer harvestlevel;
	/**
	 * The mining speed of the tool. Every block has hardness value, and the actual breaking time is calculated using the mining speed of the tool and the hardness of the block.
	 */
	public Float efficiency;
	/**
	 * How good enchants on the tool will be. A level 30 enchant on a tool with low enchantability will have more effects then a level 30 enchant on a tool with high enchantability.
	 */
	public Integer enchantability; //hoe goed het te enchanten is
	/**
	 * The name of the item used to repair the tool. If you want to use a block for repairing, use the "repairblock" variable instead.
	 */
	public String repairitem; //naam van het item dat nodig is om de tool te repareren
	/**
	 * The name of the block used to repair the tool. If you want to use an item for repairing, use the "repairitem" variable instead.
	 */
	public String repairblock; //naam van het block dat nodig is om de tool te repareren

	public ToolItemResource() {
		type = ItemType.tool;
		stacksize = 1;
	}
}
