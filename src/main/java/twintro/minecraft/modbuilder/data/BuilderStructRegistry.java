package twintro.minecraft.modbuilder.data;

import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;

public class BuilderStructRegistry implements IWorldGenerator {
	public Set<BuilderStruct> structs = new LinkedHashSet();
		
	@Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {   
		for (BuilderStruct struct: structs) {
			random = struct.generate(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
		}
	}
}
