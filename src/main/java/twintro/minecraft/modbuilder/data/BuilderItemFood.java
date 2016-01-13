package twintro.minecraft.modbuilder.data;

import java.util.Set;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

/**
 * Base class for new {@link ItemFood}s. Has two constructors, one with saturation value and one without.
 */
public class BuilderItemFood extends ItemFood {
	Set<Integer[]> effects;
	CreativeTabs[] tabs;

	/**
	 * 
	 * @param amount
	 * 		-The amount of hunger point restored when eating this food. Range from 0 to 20 (inclusive).
	 * @param isWolfFood
	 * 		-Boolean to see if this food can be eaten by wolves.
	 * @param effects
	 * 		-All potion effects the player gains when eating this item. Each effect is an int array of length 3.
	 * 		The first integer is the ID of the effect (range 1 to 23 (inclusive))
	 * 		The second integer is the duration of the effect in game ticks (20 ticks is 1 second)
	 * 		The third integer is the amplifier of the effect. In vanilla minecraft this ranges from 0 to 5, but in mods every value is usable.
	 * 		This will not change some effects (e.g. blindness).
	 */
	public BuilderItemFood(int amount, boolean isWolfFood, Set<Integer[]> effects, CreativeTabs[] tabs) {
		super(amount, isWolfFood);
		this.effects = effects;
		this.tabs = tabs;
	}

	/**
	 * 
	 * @param amount
	 * 		-The amount of hunger point restored when eating this food. Range from 0 to 20 (inclusive).
	 * @param saturation
	 * 		-The amount of saturation points the player will gain.
	 * 		This indicates how long it takes before the player will start getting hungry again.
	 * @param isWolfFood
	 * 		-Boolean to see if this food can be eaten by wolves.
	 * @param effects
	 * 		-All potion effects the player gains when eating this item. Each effect is an int array of length 3.
	 * 		The first integer is the ID of the effect (range 1 to 23 (inclusive))
	 * 		The second integer is the duration of the effect in game ticks (20 ticks is 1 second)
	 * 		The third integer is the amplifier of the effect. In vanilla minecraft this ranges from 0 to 5, but in mods every value is usable.
	 * 		This will not change some effects (e.g. blindness).
	 */
	public BuilderItemFood(int amount, float saturation, boolean isWolfFood, Set<Integer[]> effects, CreativeTabs[] tabs) {
		super(amount, saturation, isWolfFood);
		this.effects = effects;
		this.tabs = tabs;
	}
	
	@Override
	protected void onFoodEaten(ItemStack stack, World world, EntityPlayer player) {
	    super.onFoodEaten(stack, world, player);
	    if (effects!=null)
		    for (Integer[] entry : effects)
		        if (!world.isRemote && entry.length == 3)
		            player.addPotionEffect(new PotionEffect(entry[0], entry[1], entry[2]));
	}
	
	@Override
	public CreativeTabs[] getCreativeTabs() {
		return tabs;
	}
}
