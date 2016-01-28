package twintro.minecraft.modbuilder.data;

import java.util.Random;
import java.util.Set;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

/**
 * Base ground cover generation. This is used for generating things like flowers, tall grass and pumpkins.
 */
public class BuilderStructGround implements BuilderStruct{
	IBlockState block;
	Set<IBlockState> onlyonblocks;
	int dimension;
	int amountperchunk;
	
	/**
	 * Base ground cover generation.
	 * @param block
	 * 		-The block that will be generated in the world.
	 * @param onlyonblocks
	 * 		-The set of blocks the ground cover has to stand on (e.g grass for flowers, sand for cacti).
	 * 		If the set is empty or null, the ground cover can be generated on any block.
	 * @param dimension
	 * 		-The dimension the ground cover occurs in. Set to 0 for overworld, -1 for nether, or 1 for end.\\
	 * 		Warning: round cover will always generate on the highest possible space, so generating anything in the nether is useless as blocks will only try generating on top of the bedrock layer.
	 * @param amountperchunk
	 * 		-The amount of blocks the generator will try placing in a chunk.
	 * 		A try will fail if block doesn't stand on a block in the onlyonblocks parameter, or if the block is already occupied by a non-solid block.
	 */
	public BuilderStructGround(IBlockState block, Set<IBlockState> onlyonblocks, int dimension, int amountperchunk) {
		this.block = block;
		this.onlyonblocks = onlyonblocks;
		this.dimension = dimension;
		this.amountperchunk = amountperchunk;
	}
	
	@Override
	public Random generateComponent(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider){
		if (dimension == world.getWorldType().getWorldTypeID()) {
			for (int i = 0; i < amountperchunk; i++){
				int randX = random.nextInt(16);
				int randZ = random.nextInt(16);
				BlockPos pos = new BlockPos(chunkX*16+randX,0,chunkZ*16+randZ);
				int Y = world.getTopSolidOrLiquidBlock(pos).getY();
				pos=pos.add(0,Y,0);
				if (onlyonblocks!=null && onlyonblocks.size()>0) {
					if (onlyonblocks.contains(world.getBlockState(pos.add(0,-1,0))))
						if (world.getBlockState(pos).getBlock()==Blocks.air)
							world.setBlockState(pos,block);
				}
				else
					if (world.getBlockState(pos).getBlock()==Blocks.air)
						world.setBlockState(pos,block);
			}
		}
		return random;
	}
}
