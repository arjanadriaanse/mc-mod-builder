package twintro.minecraft.modbuilder.data.resources.structures;

import java.util.Set;

import net.minecraft.block.Block;

public class GroundStructureResource extends BaseStructureResource {
	public String block; //Welk block als groundcover gegenereerd moet worden
	public Set<String> onlyonblocks; //Op welke blokken het perse moet staan (e.g. dirt of sand)
	public Integer dimension; //In welke dimension gewerkt moet worden
	public Integer amountperchunk; //het aantal pogingen om deze blocks in een chunk te genereren
	
	public GroundStructureResource() {
		this.type = StructureType.ground;
	}
}
