package twintro.minecraft.modbuilder.data;

import java.util.LinkedHashSet;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.ResourceLocation;
import twintro.minecraft.modbuilder.data.resources.BaseItemResource;
import twintro.minecraft.modbuilder.data.resources.BlockResource;
import twintro.minecraft.modbuilder.data.resources.BaseBlockResource;
import twintro.minecraft.modbuilder.data.resources.FoodItemResource;
import twintro.minecraft.modbuilder.data.resources.ItemResource;
import twintro.minecraft.modbuilder.data.resources.ItemStackResource;
import twintro.minecraft.modbuilder.data.resources.ToolItemResource;

public class ResourceConverter {
	public static BuilderBlock toBlock(BlockResource resource) {
		BuilderBlock block = new BuilderBlock(ResourceHelper.materials.get(resource.material));
		if (resource.tab != null)
			block.setCreativeTab(ResourceHelper.creativeTabs.get(resource.tab));
		return block;
	}
	
	public static Block toBlock(BaseBlockResource resource) {
		if (resource instanceof BlockResource)
			return toBlock((BlockResource) resource);

		return null;
	}

	public static Item toItem(BaseItemResource resource) {
		if (resource instanceof ItemResource)
			return toItem((ItemResource) resource);
		else if (resource instanceof ToolItemResource)
			return toItem((ToolItemResource) resource);
		else if (resource instanceof FoodItemResource)
			return toItem((FoodItemResource) resource);

		return null;
	}

	public static Item toItem(ItemResource resource) {
		return new BuilderItem(resource.tabs != null ? getTabs(resource.tabs) : null);
	}

	public static ItemFood toItem(FoodItemResource resource) {
		if (resource.saturation == null)
			return new BuilderItemFood(resource.amount, resource.wolf != null ? resource.wolf : false);
		else
			return new BuilderItemFood(resource.amount, resource.saturation,
					resource.wolf != null ? resource.wolf : false);
	}

	public static ItemTool toItem(ToolItemResource resource) {
		Set blocks = new LinkedHashSet();
		if (resource.blocks != null) {
			for (String key : resource.blocks)
				blocks.add(getBlock(key));
		}
		return new BuilderItemTool(resource.damage, ToolMaterial.valueOf(resource.material.toUpperCase()), blocks);
	}

	public static ItemStack toItemStack(ItemStackResource resource) {
		Item item = null;
		if (resource.item != null)
			item = getItem(resource.item);
		Block block = null;
		if (resource.block != null)
			block = getBlock(resource.block);

		ItemStack stack = item != null ? new ItemStack(item) : new ItemStack(block);
		if (resource.amount != null)
			stack.stackSize = resource.amount;
		if (resource.meta != null)
			stack.setItemDamage(resource.meta);

		return stack;
	}

	private static Item getItem(String key) {
		return (Item) Item.itemRegistry.getObject(new ResourceLocation(key).toString());
	}

	private static Block getBlock(String key) {
		return (Block) Block.blockRegistry.getObject(new ResourceLocation(key).toString());
	}

	private static CreativeTabs[] getTabs(Set<String> keys) {
		CreativeTabs[] tabs = new CreativeTabs[keys.size()];
		int i = 0;
		for (String key : keys)
			tabs[i++] = ResourceHelper.creativeTabs.get(key);
		return tabs;
	}
}
