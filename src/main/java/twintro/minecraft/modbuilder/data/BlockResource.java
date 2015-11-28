package twintro.minecraft.modbuilder.data;

import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockResource extends Resource {
	private BuilderBlock block;
	
	public String material;
	public String model;
	public String tab;

	public BlockResource(String material, String model,String tab) {
		this.material = material;
		this.model = model;
		this.tab = tab;
	}
	
	public BuilderBlock toBlock() {
		if (block == null) {
			block = new BuilderBlock(ResourceHelper.materials.get(material), model);
			block.setCreativeTab(ResourceHelper.tabs.get(tab));
		}
		return block;
	}
}
