package twintro.minecraft.modbuilder.data.resources.structures;

import net.minecraft.block.Block;

public class OregenResource extends BaseStructureResource {
	public String block;
	public Integer dimension;
	public Integer maxveinsize;
	public Integer chancestospawn;
	public Integer minY;
	public Integer maxY;
	
	public OregenResource() {
		this.type = StructureType.ore;
	}
}
