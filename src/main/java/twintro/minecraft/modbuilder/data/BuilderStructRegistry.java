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

/**
 * The actual structure generator. This generator will generate every structure in the "structs" variable.
 */
public class BuilderStructRegistry implements IWorldGenerator {
	public Set<BuilderStruct> structs = new LinkedHashSet();
		
	@Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {   
		long seed = world.getSeed();
		for (BuilderStruct struct: structs) {
			struct.generate(new Random(seed), chunkX, chunkZ, world, chunkGenerator, chunkProvider);
			seed+=random.nextInt();
		}
	}
}
