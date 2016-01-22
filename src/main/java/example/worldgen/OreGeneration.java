package example.worldgen;

import java.util.Random;

import example.blocks.MyBlocks;
import net.minecraft.block.Block;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;

public class OreGeneration implements IWorldGenerator {
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator,
			IChunkProvider chunkProvider) {
		//-1 Nether | 0 Overworld | 1 End
		switch(world.getWorldType().getWorldTypeID()){
		case -1:
			generateNether();
			break;
		case 0:
			generateOverworld(world, random, chunkX * 16, chunkZ * 16);
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
	
	public void generateOverworld(World world, Random random, int x, int z){
		generateOre(MyBlocks.climber, world, random, x, z, 16, 32, 1, 64);
	}
	
	public void generateEnd(){
		
	}
	
	public void generateOre(Block block, World world, Random random, int chunkX, int chunkZ,
			int maxVeinSize, int chancesToSpawn, int minY, int maxY){
		int heightRange = maxY - minY;
		WorldGenMinable wgm = new WorldGenMinable(block.getDefaultState(), maxVeinSize);
		for (int i = 0; i < chancesToSpawn; i++){
			int randX = random.nextInt(16);
			int randY = random.nextInt(heightRange) + minY;
			int randZ = random.nextInt(16);
			wgm.generate(world, random, new BlockPos(chunkX + randX, randY, chunkZ + randZ));
		}
	}
}
