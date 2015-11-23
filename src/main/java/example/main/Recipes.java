package example.main;

import example.blocks.MyBlocks;
import example.items.MyItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class Recipes {
	public static void init(){
		GameRegistry.addRecipe(new ItemStack(MyItems.wand), 
				" r ",
				"rsr",
				" s ",
				'r', new ItemStack(Item.getByNameOrId("minecraft:redstone")),
				's', new ItemStack(Item.getByNameOrId("minecraft:stick")));
		
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.clay), 
				new ItemStack(MyBlocks.climber), new ItemStack(Blocks.cobblestone));
		
		GameRegistry.addSmelting(new ItemStack(Item.getByNameOrId("minecraft:egg")),
				new ItemStack(MyItems.cookedEgg), 0.35F);
	}
}
