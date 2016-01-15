package twintro.minecraft.modbuilder.data.resources.recipes;

import java.util.HashMap;
import java.util.Map;

/**
 * Contains properties for item stacks (multiple blocks or items, not just a single one).
 */
public class ItemStackResource {
	/**
	 * The item in the stack. If you want a block instead, use "block".
	 */
	public String item;
	/**
	 * What the item will return when used for crafting in this particular recipe.
	 * This needs to be an item; it won't work for blocks.
	 * For example: milk buckets will return an empty bucket when used to craft a cake.
	 * If you want an item that has a container property (so it normally returns items when used for crafting) to not return an item in this particular recipe, let this be an empty string.
	 */
	public String container;
	/**
	 * The block in the stack. If you want an item instead, use "item".
	 */
	public String block;
	/**
	 * The amount of blocks or items in this stack.
	 */
	public Integer amount;
	/**
	 * A random increase of the amount of blocks or items in this stack. Only used to define the randomness in block drops.
	 */
	public Integer amountincrease;
	/**
	 * The metadata of the block or item in this stack.
	 * For example, white wool is the wool block with metadata 0, whilst orange wool is the wool block with metadata 1.
	 */
	public Integer meta;
	/**
	 * A map of all the enchantments the item has.
	 * The string is the name of the enchantment, and the integer is the enchantment level (for example, a sharpness V sword is saved as {"sharpness":5}).
	 */
	public Map<String, Integer> enchantments = new HashMap<String, Integer>();
}
