package twintro.minecraft.modbuilder.data;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;

/**
 * Base ore generation. This is used for generating all ores, but also for things like dirt or gravel pockets underground.
 */
public class BuilderStructOre implements BuilderStruct{
	Block block;
	int dimension;
	int maxVeinSize;
	int chancesToSpawn;
	int minY;
	int maxY;

	/**
	 * Base ore generation
	 * @param block
	 * 		-The block that has to be generated.
	 * @param dimension
	 * 		-The dimension the block needs to be generated in.
	 * @param maxVeinSize
	 * 		-The maximum amount of blocks in one ore vein.
	 * @param chancesToSpawn
	 * 		-The amount of ore veins the generator will try to create in one chunk.
	 * 		Sometimes parts of the vein will be cut off by a cave, resulting in seemingly smaller veins.
	 * @param minY
	 * 		The minimum Y-level the ore will be generated on.
	 * @param maxY
	 * 		The maximum Y-level the ore will be generated on.\\
	 * 		Note that the Y-level range is for the center of the vein; it is possible that some blocks are outside the range.
	 */
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
