package twintro.minecraft.modbuilder.data;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.IFuelHandler;

public class FuelHandler implements IFuelHandler{
	private Map<ItemStack, Integer> fuellist;
	
	public FuelHandler(HashMap<ItemStack, Integer> fuellist) {
		this.fuellist = fuellist;
	}
	
	@Override
	public int getBurnTime(ItemStack fuel) {
		for (Entry<ItemStack, Integer> entry : fuellist.entrySet())
			if (fuel.getItem() == entry.getKey().getItem() )
				return entry.getValue();
		return 0;
	}
}
