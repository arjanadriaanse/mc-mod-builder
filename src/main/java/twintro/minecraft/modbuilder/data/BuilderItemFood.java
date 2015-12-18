package twintro.minecraft.modbuilder.data;

import java.util.Set;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class BuilderItemFood extends ItemFood {
	Set<Integer[]> effects;

	public BuilderItemFood(int amount, boolean isWolfFood, Set<Integer[]> effects) {
		super(amount, isWolfFood);
		this.effects = effects;
	}

	public BuilderItemFood(int amount, float saturation, boolean isWolfFood, Set<Integer[]> effects) {
		super(amount, saturation, isWolfFood);
		this.effects = effects;
	}
	
	@Override
	protected void onFoodEaten(ItemStack stack, World world, EntityPlayer player) {
	    super.onFoodEaten(stack, world, player);
	    if (effects!=null)
		    for (Integer[] entry : effects)
		        if (!world.isRemote && entry.length == 3)
		            player.addPotionEffect(new PotionEffect(entry[0], entry[1], entry[2]));
	}

}
