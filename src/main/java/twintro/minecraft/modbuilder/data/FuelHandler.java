package twintro.minecraft.modbuilder.data;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.IFuelHandler;

public class FuelHandler implements IFuelHandler{
	private Map<ItemStack, Integer> fuels;
	
	public FuelHandler(Map<ItemStack, Integer> fuels) {
		this.fuels = fuels;
	}
	
	@Override
	public int getBurnTime(ItemStack fuel) {
		if (this.fuels.containsKey(fuel))
			return this.fuels.get(fuel);
		else
			return 0;
	}
}
