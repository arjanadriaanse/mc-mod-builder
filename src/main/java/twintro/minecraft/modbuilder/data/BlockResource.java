package twintro.minecraft.modbuilder.data;

public class BlockResource extends Resource {
	public String material;

	public String model;
	public String tab;
	private BuilderBlock block;

	public BlockResource(String material, String model, String tab) {
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
