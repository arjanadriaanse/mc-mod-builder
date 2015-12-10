package example.items;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;

public class MyItems {
	public static Item wand;
	
	public static void preInit(){
		wand = new ItemWand();
	}
	
	public static void init(FMLInitializationEvent event){
		if (event.getSide() == Side.CLIENT){
			((ItemWand)wand).renderItem();
		}
	}
}
