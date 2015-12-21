package twintro.minecraft.modbuilder.data;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import example.main.ModInformation;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import twintro.minecraft.modbuilder.data.resources.TabResource;
import twintro.minecraft.modbuilder.data.resources.blocks.BaseBlockResource;
import twintro.minecraft.modbuilder.data.resources.blocks.BlockResource;
import twintro.minecraft.modbuilder.data.resources.items.BaseItemResource;
import twintro.minecraft.modbuilder.data.resources.items.FoodItemResource;
import twintro.minecraft.modbuilder.data.resources.items.ItemResource;
import twintro.minecraft.modbuilder.data.resources.items.ToolItemResource;
import twintro.minecraft.modbuilder.data.resources.recipes.ItemStackResource;
import twintro.minecraft.modbuilder.data.resources.structures.BaseStructureResource;
import twintro.minecraft.modbuilder.data.resources.structures.OregenResource;

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
		if (resource.container != null)
			item.setContainerItem(Item.getByNameOrId(resource.container));
		return item;
	}

	public static ItemFood toItem(final FoodItemResource resource) {
		BuilderItemFood item;
		if (resource.saturation == null)
			item = new BuilderItemFood(resource.amount, resource.wolf != null ? resource.wolf : false, resource.effects);
		else
			item = new BuilderItemFood(resource.amount, resource.saturation,
					resource.wolf != null ? resource.wolf : false, resource.effects);
		if (resource.stacksize != null)
			item.setMaxStackSize(resource.stacksize);
		if (resource.container != null)
			item.setContainerItem(Item.getByNameOrId(resource.container));
		if (resource.alwaysedible != null)
			if (resource.alwaysedible)
				item.setAlwaysEdible();
		return item;
	}

	public static ItemTool toItem(ToolItemResource resource) {
		ToolMaterial material = EnumHelper.addToolMaterial(
				resource.name           != null ? resource.name           : "",
				resource.harvestlevel   != null ? resource.harvestlevel   : 2,
				resource.durability     != null ? resource.durability     : 250,
				resource.efficiency     != null ? resource.efficiency     : 6.0F,
				resource.damage         != null ? resource.damage         : 0.5F,
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
		if (resource.container != null)
			item.setContainerItem(Item.getByNameOrId(resource.container));
		return item;
	}
	
	public static IWorldGenerator toStructure(BaseStructureResource resource) {
		if (resource instanceof OregenResource)
			return toStructure((OregenResource) resource);
		return null;
	}
	
	public static IWorldGenerator toStructure(OregenResource resource) {
		BuilderOregen structure = new BuilderOregen(
				Block.getBlockFromName(resource.block),
				resource.dimension      !=null ? resource.dimension      : 0,
				resource.maxveinsize    !=null ? resource.maxveinsize    : 8,
				resource.chancestospawn !=null ? resource.chancestospawn : 16,
				resource.minY           !=null ? resource.minY           : 1,
				resource.maxY           !=null ? resource.maxY           : 64);
		return structure;
	}

	public static ItemStack toItemStack(ItemStackResource resource) {
		Item item = null;
		if (resource.item != null) {
			item = Item.getByNameOrId(resource.item);
			if (resource.container != null)
				item.setContainerItem(resource.container == "" ? null : Item.getByNameOrId(resource.container));
		}
		Block block = null;
		if (resource.block != null)
			block = Block.getBlockFromName(resource.block);

		ItemStack stack = item != null ?
				new ItemStack(item, resource.amount != null ? resource.amount: 1) :
				new ItemStack(block, resource.amount != null ? resource.amount: 1);
		if (resource.meta != null)
			stack.setItemDamage(resource.meta);
		for (Entry<String, Integer> enchant : resource.enchantments.entrySet())
			stack.addEnchantment(Enchantment.getEnchantmentByLocation(enchant.getKey()), enchant.getValue());
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
