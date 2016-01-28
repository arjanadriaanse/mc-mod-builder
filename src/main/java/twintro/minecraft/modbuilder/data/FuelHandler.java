package twintro.minecraft.modbuilder.data;

import java.util.Map;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.IFuelHandler;

/**
 * The loader for all fuels. Every item that can be used as fuel has to be set into the "fuels" variable.
 * The fuels variable is a map of itemstacks and integers.
 * The itemstack is the item/block that can be used as fuel, and the integer is the amount of ticks it will burn.
 * For reference, 20 ticks is 1 second, and 10 seconds (or 200 ticks) is the amount of time it takes to cook/smelt 1 item.
 */
public class FuelHandler implements IFuelHandler{
	private Map<Item, Integer> fuels;
	
	public FuelHandler(Map<Item, Integer> fuels) {
		this.fuels = fuels;
	}
	
	@Override
	public int getBurnTime(ItemStack fuel) {
		if (this.fuels.containsKey(fuel.getItem()))
			return this.fuels.get(fuel.getItem());
		else
			return 0;
	}
}
