package twintro.minecraft.modbuilder.data;

import java.util.LinkedHashSet;
import java.util.Set;

import example.main.ModInformation;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import twintro.minecraft.modbuilder.data.resources.TabResource;
import twintro.minecraft.modbuilder.data.resources.blocks.BaseBlockResource;
import twintro.minecraft.modbuilder.data.resources.blocks.BlockResource;
import twintro.minecraft.modbuilder.data.resources.items.BaseItemResource;
import twintro.minecraft.modbuilder.data.resources.items.FoodItemResource;
import twintro.minecraft.modbuilder.data.resources.items.ItemResource;
import twintro.minecraft.modbuilder.data.resources.items.ToolItemResource;
import twintro.minecraft.modbuilder.data.resources.recipes.ItemStackResource;

/**
 * Contains methods for converting resource objects to Minecraft objects.
 */
public class ResourceConverter {
	public static Block toBlock(BaseBlockResource resource) {
		if (resource instanceof BlockResource)
			return toBlock((BlockResource) resource);
		return null;
	}

	public static BuilderBlock toBlock(BlockResource resource) {
		BuilderBlock block = new BuilderBlock(ResourceHelper.materials.get(resource.material));
		//setUnlocalizedName(ModInformation.MODID + "_" + name);
		if (resource.tab != null)
			block.setCreativeTab(ResourceHelper.tabs.get(resource.tab));
		if (resource.light != null)
			block.setLightLevel(((float)resource.light)/15);
		if (resource.opacity != null)
			block.setLightOpacity(resource.opacity);
		if (resource.slipperiness != null)
			block.slipperiness = resource.slipperiness;
		if (resource.hardness != null)
			block.setHardness(resource.hardness);
		else
			block.setHardness(1);
		if (resource.resistance != null)
			block.setResistance(resource.resistance);
		if (resource.unbreakable != null)
			if (resource.unbreakable)
				block.setBlockUnbreakable();
		if (resource.harvesttype != null && resource.harvestlevel != null)
			block.setHarvestLevel(resource.harvesttype, resource.harvestlevel);
		return block;
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
		BuilderItem item = new BuilderItem(resource.tabs != null ? getTabs(resource.tabs) : null);
		if (resource.stacksize != null)
			item.setMaxStackSize(resource.stacksize);
		return item;
	}

	public static ItemFood toItem(FoodItemResource resource) {
		BuilderItemFood item;
		if (resource.saturation == null)
			item = new BuilderItemFood(resource.amount, resource.wolf != null ? resource.wolf : false);
		else
			item = new BuilderItemFood(resource.amount, resource.saturation,
					resource.wolf != null ? resource.wolf : false);
		if (resource.stacksize != null)
			item.setMaxStackSize(resource.stacksize);
		return item;
	}

	public static ItemTool toItem(ToolItemResource resource) {
		ToolMaterial material = EnumHelper.addToolMaterial(
				resource.name           != null ? resource.name           : "",
				resource.harvestlevel   != null ? resource.harvestlevel   : 2,
				resource.durability     != null ? resource.durability     : 250,
				resource.efficiency     != null ? resource.efficiency     : 6.0F,
				resource.damage         != null ? resource.damage         : 2.0F,
				resource.enchantability != null ? resource.enchantability : 10);
		if (resource.repairitem != null) {
				ItemStack repair = new ItemStack(Item.getByNameOrId(resource.repairitem));
				material.setRepairItem(repair);
		}
		else if (resource.repairblock != null) {
				ItemStack repair = new ItemStack(Block.getBlockFromName(resource.repairblock));
				material.setRepairItem(repair);
		}
		Set blocks = new LinkedHashSet();
		if (resource.blocks != null) {
			for (String key : resource.blocks)
				blocks.add(Block.getBlockFromName(key));
		}
		BuilderItemTool item = new BuilderItemTool(material.getDamageVsEntity(), material, blocks);
		if (resource.stacksize != null)
			item.setMaxStackSize(resource.stacksize);
		return item;
	}

	public static ItemStack toItemStack(ItemStackResource resource) {
		Item item = null;
		if (resource.item != null)
			item = Item.getByNameOrId(resource.item);
		Block block = null;
		if (resource.block != null)
			block = Block.getBlockFromName(resource.block);

		ItemStack stack = item != null ? new ItemStack(item) : new ItemStack(block);
		if (resource.amount != null)
			stack.stackSize = resource.amount;
		if (resource.meta != null)
			stack.setItemDamage(resource.meta);

		return stack;
	}

	private static CreativeTabs[] getTabs(Set<TabResource> keys) {
		CreativeTabs[] tabs = new CreativeTabs[keys.size()];
		int i = 0;
		for (TabResource key : keys)
			tabs[i++] = ResourceHelper.tabs.get(key);
		return tabs;
	}
}
