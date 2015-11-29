package twintro.minecraft.modbuilder.data.resources;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemStackResource {
	public Integer amount;
	public String block;
	public String item;
	public Integer meta;

	public ItemStack toItemStack() {
		Item item = null;
		if (this.item != null) {
			ResourceLocation location = new ResourceLocation(this.item);
			item = GameRegistry.findItem(location.getResourceDomain(), location.getResourcePath());
		}
		Block block = null;
		if (this.block != null) {
			ResourceLocation location = new ResourceLocation(this.block);
			block = GameRegistry.findBlock(location.getResourceDomain(), location.getResourcePath());
		}

		ItemStack stack = item != null ? new ItemStack(item) : new ItemStack(block);
		if (amount != null)
			stack.stackSize = amount;
		if (meta != null)
			stack.setItemDamage(meta);

		return stack;
	}
}
