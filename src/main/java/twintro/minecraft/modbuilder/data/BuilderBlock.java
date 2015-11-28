package twintro.minecraft.modbuilder.data;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BuilderBlock extends Block {

	public String model;

	public BuilderBlock(Material material, String model) {
		super(material);
		this.model = model;
	}
}
