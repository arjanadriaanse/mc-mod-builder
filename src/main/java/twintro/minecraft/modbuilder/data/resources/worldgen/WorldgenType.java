package twintro.minecraft.modbuilder.data.resources.worldgen;

public enum WorldgenType {
	ore(OregenResource.class);

	private Class<? extends BaseWorldgenResource> value;

	private WorldgenType(Class<? extends BaseWorldgenResource> value) {
		this.value = value;
	}

	public Class<? extends BaseWorldgenResource> getValue() {
		return (value);
	}
}
