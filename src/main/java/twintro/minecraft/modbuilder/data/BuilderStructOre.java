package twintro.minecraft.modbuilder.data;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;

public class BuilderStructOre implements BuilderStruct{
	Block block;
	int dimension;
	int maxVeinSize;
	int chancesToSpawn;
	int minY;
	int maxY;

	public BuilderStructOre(Block block, int dimension, int maxVeinSize, int chancesToSpawn, int minY, int maxY) {
		this.block = block;
		this.dimension = dimension;
		this.maxVeinSize = maxVeinSize;
		this.chancesToSpawn = chancesToSpawn;
		this.minY = minY;
		this.maxY = maxY;
	}
	
	@Override
	public Random generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider){
		if (dimension == world.getWorldType().getWorldTypeID()) {
			int heightRange = maxY - minY;
			WorldGenMinable wgm = new WorldGenMinable(block.getDefaultState(), maxVeinSize);
			for (int i = 0; i < chancesToSpawn; i++){
				int randX = random.nextInt(16);
				int randY = random.nextInt(heightRange) + minY;
				int randZ = random.nextInt(16);
				wgm.generate(world, random, new BlockPos(16*chunkX + randX, randY, 16*chunkZ + randZ));
			}
		}
		return random;
	}

}
