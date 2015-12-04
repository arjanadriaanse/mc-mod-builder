package twintro.minecraft.modbuilder.data.resources.blocks;

import twintro.minecraft.modbuilder.data.resources.MaterialResource;
import twintro.minecraft.modbuilder.data.resources.TabResource;

public abstract class BaseBlockResource {
	public BlockType type;
	public MaterialResource material;
	public String model;
	public TabResource tab;
}
