package twintro.minecraft.modbuilder.data.resources.blocks;

import twintro.minecraft.modbuilder.data.resources.MaterialResource;
import twintro.minecraft.modbuilder.data.resources.TabResource;

public abstract class BaseBlockResource {
	public BlockType type;
	public MaterialResource material;
	public String model;
	public TabResource tab;
	
	public Integer light; //light level (0-15)
	public Integer opacity; //geeft aan hoeveel licht door het block wordt doorgelaten (0-15)
	public Float slipperiness; //gladheid van het block
	public Float hardness; //hoe lang het duurt om het block kapot te maken
	public Float resistance; //hoe goed het block tegen explosies bestand is
	public Boolean unbreakable; //of het block in survival kapot te maken is
	public String harvesttype; //met welke tool het block sneller kapot gaat
	public Integer harvestlevel; //welk level deze tool moet zijn (wood=0,stone=1,etc)
}
