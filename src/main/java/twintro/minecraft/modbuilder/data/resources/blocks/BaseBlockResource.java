package twintro.minecraft.modbuilder.data.resources.blocks;

import java.util.List;

import twintro.minecraft.modbuilder.data.resources.MaterialResource;
import twintro.minecraft.modbuilder.data.resources.TabResource;
import twintro.minecraft.modbuilder.data.resources.recipes.ItemStackResource;

/**
 * Contains all basic properties for blocks.
 */
public abstract class BaseBlockResource {
	/**
	 * The type of the block. Currently there is only one default type: regular.
	 */
	public BlockType type;
	/**
	 * The blockmaterial. This contains properties such as sound when breaking a block or flammability.
	 */
	public MaterialResource material; //TODO remake materials as properties
	public Boolean flammable;
	public Integer firespreadspeed;
	public Integer flammability;
	public Boolean replaceable;
	public Boolean requirestool;
	public Integer mobility;
	public Boolean solid;
	public Boolean opaque;
	public Boolean cutout;
	public Integer mapcolor;
	/**
	 * The name of the itemmodel that the item will use.
	 */
	public String model;
	/**
	 * The name of the creative tab the item is in.
	 */
	public TabResource tab;
	/**
	 * The items that will be dropped by the block.
	 */
	public List<ItemStackResource> drops;
	/**
	 * How much light the block will omit. This is a value from 0 to 15. Most blocks are lightfree and have lightlevel 0.
	 * Torches or other lightsources have lightlevel 15.
	 */
	public Integer light;
	/**
	 * The opposite of transparency; how much the lightlevel will decrease when passing through the block.
	 * 0 means no light will pass, and 15 means all light will pass as if the block is air.
	 * Some blocks have values in between. Ice for example has an opacity of 7.
	 */
	public Integer opacity;
	/**
	 * How slippery the block is. Most blocks have a value of 0.6, but for example ice has 1. Slipperiness higher than 1 is not recommended.
	 */
	public Float slipperiness;
	/**
	 * How resistant the block is to breaking by a player. The actual breaking time depends on this value and the tool the player is using.
	 */
	public Float hardness;
	/**
	 * How resistant the block is to breaking by explosions. These values are quite strange:
	 * most blocks have a resistance of 10-15, some more resistant blocks (like obsidian) have a value of 3000, and unbreakable blocks have a value of 18 million.
	 * Barrier block has a resistance value of 18 million and 3.
	 */
	public Float resistance;
	/**
	 * Checks if the block is unbreakable in survival. In vanilla minecraft, this applies to bedrock, barrier block and  nether/end portal blocks.
	 */
	public Boolean unbreakable;
	/**
	 * The tool required to break the block. Most stone like blocks require a pickaxe and most wood like blocks require an axe. Others require a shovel.
	 * Values other than "pickaxe" "axe" or "shovel" will not work.
	 */
	public String harvesttype;
	/**
	 * The level of the tool required to break the block. Wood = 0, stone = 1, iron = 2 and diamond/gold = 3.
	 * For example, cobblestone requires at least a wooden pickaxe and therefore has a harvestlevel of 0.
	 * Iron ore requires a stone pickaxe and therefore has a harvestlevel of 1.
	 * Obsidian requires a diamond pickaxe and has a harvestlevel of 3.
	 */
	public Integer harvestlevel;
	/**
	 * The length in game ticks that the item will burn when put inside a furnace. One second is 20 ticks. One item takes 10 seconds (or 200 ticks) to cook/smelt.
	 */
	public Integer burntime;
}
