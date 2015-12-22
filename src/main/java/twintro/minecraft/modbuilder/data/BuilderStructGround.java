package twintro.minecraft.modbuilder.data;

import java.util.Random;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;

public class BuilderStructGround implements IWorldGenerator {
	Block block;
	Set<Block> onlyonblocks;
	int dimension;
	int amountperchunk;
	
	public BuilderStructGround(Block block, Set<Block> onlyonblocks, int dimension, int amountperchunk) {
		this.block = block;
		this.onlyonblocks = onlyonblocks;
		this.dimension = dimension;
		this.amountperchunk = amountperchunk;
	}
	
	@Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {   
		if (dimension == world.getWorldType().getWorldTypeID()) {
			generateGround(world, random, chunkX, chunkZ);
		}
	}
	
	public void generateGround(World world, Random random, int chunkX, int chunkZ){
		for (int i = 0; i < amountperchunk; i++){
			int randX = random.nextInt(16);
			int randZ = random.nextInt(16);
			BlockPos pos = new BlockPos(chunkX*16+randX,0,chunkZ*16+randZ);
			int Y = world.getTopSolidOrLiquidBlock(pos).getY();
			pos=pos.add(0,Y,0);
			if (onlyonblocks!=null)
				if (onlyonblocks.contains(world.getBlockState(pos.add(0,-1,0)).getBlock()))
					if (world.getBlockState(pos).getBlock()==Blocks.air)
						world.setBlockState(pos,block.getDefaultState());
			else
				if (world.getBlockState(pos).getBlock()==Blocks.air)
					world.setBlockState(pos,block.getDefaultState());
		}
	}

}
