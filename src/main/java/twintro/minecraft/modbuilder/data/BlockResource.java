package twintro.minecraft.modbuilder.data;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockResource extends Resource {
	private BuilderBlock block;
	public String material;
	public String model;

	public BlockResource(String material, String model) {
		this.material = material;
		this.model = model;
	}
	
	public BuilderBlock toBlock() {
		if (block == null)
			block = new BuilderBlock(ResourceHelper.materials.get(material), model);
		
		return block;
	}
}
