package twintro.minecraft.modbuilder.data.resources.structures;

/**
 * Contains all basic properties for structures.
 */
public abstract class BaseStructureResource {
	/**
	 * The type of the structure.
	 */
	public StructureType type;
	/**
	 * The block that needs to be generated as an ore.
	 */
	public String block;
	/**
	 * The dimension the ore needs to generate in. Use -1 for nether, 0 for overworld or 1 for the end.
	 */
	public Integer dimension;
}
