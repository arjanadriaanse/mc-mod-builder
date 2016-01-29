package twintro.minecraft.modbuilder.data;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockHelper;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;

/**
 * Base ore generation. This is used for generating all ores, but also for things like dirt or gravel pockets underground.
 */
public class BuilderStructOre implements BuilderStruct{
	IBlockState block;
	IBlockState replaceblock;
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
	public BuilderStructOre(IBlockState block, IBlockState replaceblock, int dimension, int maxVeinSize, int chancesToSpawn, int minY, int maxY) {
		this.block = block;
		this.dimension = dimension;
		this.maxVeinSize = maxVeinSize;
		this.chancesToSpawn = chancesToSpawn;
		this.minY = minY;
		this.maxY = maxY;
		if (replaceblock==null)
			switch (dimension) {
				case -1: {
					replaceblock=Blocks.netherrack.getDefaultState();
					break;
				}
				case 1: {
					replaceblock=Blocks.end_stone.getDefaultState();
					break;
				}
				default: {
					replaceblock=Blocks.stone.getDefaultState();
				}
			}
		this.replaceblock = replaceblock;
	}
	
	@Override
	public Random generateComponent(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider){
		if (dimension == world.getWorldType().getWorldTypeID()) {
			int heightRange = maxY - minY;
			WorldGenMinable wgm = new WorldGenMinable(block, maxVeinSize, BlockHelper.forBlock(replaceblock.getBlock()));
			for (int i = 0; i < chancesToSpawn; i++){
				int randX = random.nextInt(16);
				int randY = random.nextInt(heightRange) + minY;
				int randZ = random.nextInt(16);
				BlockPos pos = new BlockPos(16*chunkX + randX, randY, 16*chunkZ + randZ);
				if (maxVeinSize>3)
					wgm.generate(world, random, pos);
				else {
					if (world.getBlockState(pos).equals(replaceblock))
						world.setBlockState(pos, block);
					for (int j = 1; j < maxVeinSize; j++){
						int X = Math.min(Math.max(randX + random.nextInt(3)-1, 0),15);
						int Y = randY + random.nextInt(3)-1;
						int Z = Math.min(Math.max(randZ + random.nextInt(3)-1, 0),15);
						BlockPos newpos = new BlockPos(16*chunkX + X, Y, 16*chunkZ + Z);
						if (world.getBlockState(newpos).equals(replaceblock))
							world.setBlockState(newpos, block);
						
					}
				}
			}
		}
		return random;
	}

}
