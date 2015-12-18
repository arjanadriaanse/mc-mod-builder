package twintro.minecraft.modbuilder.data.resources.worldgen;

import net.minecraft.block.Block;

public class OregenResource extends BaseWorldgenResource {
	public String block;
	public Integer dimension;
	public Integer maxveinsize;
	public Integer chancestospawn;
	public Integer miny;
	public Integer maxy;
	
	public OregenResource() {
		this.type = WorldgenType.ore;
	}
}
