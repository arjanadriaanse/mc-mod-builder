package example.worldgen;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.fml.common.IWorldGenerator;

public class LollyGeneration implements IWorldGenerator {
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator,
			IChunkProvider chunkProvider) {
		switch(world.getWorldType().getWorldTypeID()){
		case -1:
			generateNether();
			break;
		case 0:
			generateOverworld(world, random, chunkX, chunkZ);
			break;
		case 1:
			generateEnd();
			break;
		default:
			break;
		}
	}
	
	public void generateNether(){
		
	}
	
	public void generateOverworld(World world, Random random, int chunkX, int chunkZ){
		//TODO make lollypop
	}
	
	public void generateEnd(){
		
	}
}
