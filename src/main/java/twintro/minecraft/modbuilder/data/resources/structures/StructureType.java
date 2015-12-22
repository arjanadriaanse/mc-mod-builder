package twintro.minecraft.modbuilder.data.resources.structures;

public enum StructureType {
	ore(OreStructureResource.class),
	ground(GroundStructureResource.class);

	private Class<? extends BaseStructureResource> value;

	private StructureType(Class<? extends BaseStructureResource> value) {
		this.value = value;
	}

	public Class<? extends BaseStructureResource> getValue() {
		return (value);
	}
}
