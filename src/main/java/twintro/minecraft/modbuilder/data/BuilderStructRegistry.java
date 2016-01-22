package twintro.minecraft.modbuilder.data;

import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.fml.common.IWorldGenerator;

/**
 * The actual structure generator. This generator will generate every structure in the "structs" variable.
 */
public class BuilderStructRegistry implements IWorldGenerator {
	public Set<BuilderStruct> structs = new LinkedHashSet();
		
	@Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {   
		for (BuilderStruct struct: structs) {
			int a=random.nextInt();
			int b=random.nextInt();
			random = struct.generateComponent(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
			int c=random.nextInt();
			int d=random.nextInt();
		}
	}
}
