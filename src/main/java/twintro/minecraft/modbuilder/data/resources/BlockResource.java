package twintro.minecraft.modbuilder.data.resources;

import twintro.minecraft.modbuilder.data.BuilderBlock;
import twintro.minecraft.modbuilder.data.ResourceHelper;

public class BlockResource {
	public String material;
	public String model;
	public String tab;

	public BuilderBlock toBlock() {
		BuilderBlock block = new BuilderBlock(ResourceHelper.materials.get(material), model);
		block.setCreativeTab(ResourceHelper.tabs.get(tab));
		return block;
	}
}
