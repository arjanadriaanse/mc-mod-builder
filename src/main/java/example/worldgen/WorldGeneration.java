package example.worldgen;

import net.minecraftforge.fml.common.registry.GameRegistry;

public class WorldGeneration {
	public static void init(){
		OreGeneration og = new OreGeneration();
		GameRegistry.registerWorldGenerator(og, 0);
		LollyGeneration lg = new LollyGeneration();
		GameRegistry.registerWorldGenerator(lg, 0);//TODO 0=max
	}
}
