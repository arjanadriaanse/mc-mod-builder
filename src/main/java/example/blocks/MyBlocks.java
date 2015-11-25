package example.blocks;

import net.minecraft.block.Block;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;

public class MyBlocks {
	public static Block climber;
	public static Block colorblock;
	
	public static void preInit(){
		climber = new BlockClimber();
		colorblock = new ColorBlock();
	}
	
	public static void init(FMLInitializationEvent event){
		if (event.getSide() == Side.CLIENT){
			((BlockClimber)climber).renderItem();
			((ColorBlock)colorblock).renderItem();
		}
	}
}
