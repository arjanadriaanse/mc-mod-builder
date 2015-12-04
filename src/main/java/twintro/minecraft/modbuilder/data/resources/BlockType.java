package twintro.minecraft.modbuilder.data.resources;

public enum BlockType {
	regular(BlockResource.class);

	private Class<? extends BaseBlockResource> value;

	private BlockType(Class<? extends BaseBlockResource> value) {
		this.value = value;
	}

	public Class<? extends BaseBlockResource> getValue() {
		return (value);
	}
}
