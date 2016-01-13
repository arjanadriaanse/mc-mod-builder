package twintro.minecraft.modbuilder.data.resources.items;

/**
 * Contains all basic properties for items.
 */
public abstract class BaseItemResource {
	/**
	 * The type of the block. Currently there is are three types: regular, food and tool.
	 */
	public ItemType type;
	/**
	 * The name of the itemmodel this item uses.
	 */
	public String model;
	/**
	 * How many items one stack can contain. Usually this is 64 (which is also the maximum).
	 * Tools should only have 1 item max, or else the durability is bugged.
	 */
	public Integer stacksize;
	/**
	 * Name of the item it will change in when you craft with it. This must be an item; it cannot be a block.
	 * For example, a milk bucket will become an empty bucket when you craft a cake.
	 */
	public String container;
	/**
	 * The length in game ticks that the item will burn when put inside a furnace. One second is 20 ticks. One item takes 10 seconds (or 200 ticks) to cook/smelt.
	 */
	public Integer burntime;
}
