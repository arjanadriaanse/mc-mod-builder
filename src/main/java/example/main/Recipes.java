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
		
		GameRegistry.addRecipe(new ItemStack(MyBlocks.colorblock,8), 
				"bwb",
				"wrw",
				"bwb",
				'r', new ItemStack(Item.getByNameOrId("minecraft:redstone")),
				'b', new ItemStack(Item.getByNameOrId("minecraft:stained_hardened_clay"),1,15),
				'w', new ItemStack(Item.getByNameOrId("minecraft:stained_hardened_clay"),1,0));
		
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.clay), 
				new ItemStack(MyBlocks.climber), new ItemStack(Blocks.cobblestone));
		
		GameRegistry.addSmelting(new ItemStack(Item.getByNameOrId("minecraft:egg")),
				new ItemStack(MyItems.cookedEgg), 0.35F);
		
		GameRegistry.addShapelessRecipe(new ItemStack(MyItems.peeledEgg),
				new ItemStack(MyItems.cookedEgg));
				
	}
}
