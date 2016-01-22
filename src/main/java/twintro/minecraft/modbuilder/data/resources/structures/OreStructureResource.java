package twintro.minecraft.modbuilder.data.resources.structures;

/**
 * The ore structure type.
 */
public class OreStructureResource extends BaseStructureResource {
	/**
	 * The block that the ore will replace. If set to null, it will default to stone in the overworld, netherrack in the nether and endstone in the end.
	 */
	public String replaceblock;
	/**
	 * The maximum amount of ore blocks the generator will place in one vein. Not all veins will be this size, as some might be cut off by a cave or other ores.
	 */
	public Integer maxveinsize;
	/**
	 * The amount of ore veins the generator will try to place in one chunk.
	 */
	public Integer chancestospawn;
	/**
	 * The minimal Y level the ore will spawn in. Note that this counts for the center of the vein, so some blocks might be below this Y level.
	 */
	public Integer minY;
	/**
	 * The maximal Y level the ore will spawn in. Note that this counts for the center of the vein, so some blocks might be above this Y level.
	 */
	public Integer maxY;
	
	public OreStructureResource() {
		this.type = StructureType.ore;
	}
}
