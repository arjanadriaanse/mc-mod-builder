package twintro.minecraft.modbuilder.data;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

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
