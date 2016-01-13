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

/**
 *	Interface for every base structure generation class.
 */
public abstract interface BuilderStruct {
	/**
	 * Method used by subclasses to generate structures in the world.
	 * @param random
	 * 		-The random object used for randomizing the structures (dependent on world seed).
	 * @param chunkX
	 * 		-The X-coordinate of the chunk that needs to be generated.
	 * @param chunkZ
	 * 		-The Z-coordinate of the chunk that needs to be generated.
	 * @param world
	 * 		-The world that needs to be generated.
	 * @param chunkGenerator
	 * 		-The chunk generator used by minecraft.
	 * @param chunkProvider
	 * 		-The chunk provider used by minecraft.
	 * @return
	 */
	public abstract Random generateComponent(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider);
}
