package twintro.minecraft.modbuilder.data.resources;

public enum BlockType {
	regular(BlockResource.class);
	
	private Class value;
	
	private BlockType(Class value){
		this.value = value;
	}
	
	public Class getValue() {
		return (value);
	}
}
