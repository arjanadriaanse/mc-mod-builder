package twintro.minecraft.modbuilder.data.resources;

import net.minecraft.block.material.Material;

public enum MaterialResource {
	air(Material.air), 
	grass(Material.grass), 
	ground(Material.ground), 
	wood(Material.wood), 
	rock(Material.rock), 
	iron(Material.iron), 
	anvil(Material.anvil), 
	water(Material.water), 
	lava(Material.lava), 
	leaves(Material.leaves), 
	plants(Material.plants), 
	vine(Material.vine), 
	sponge(Material.sponge), 
	cloth(Material.cloth), 
	fire(Material.fire), 
	sand(Material.sand), 
	circuits(Material.circuits), 
	carpet(Material.carpet), 
	glass(Material.glass), 
	redstoneLight(Material.redstoneLight), 
	tnt(Material.tnt), 
	coral(Material.coral), 
	ice(Material.ice), 
	packedIce(Material.packedIce), 
	snow(Material.snow), 
	craftedSnow(Material.craftedSnow), 
	cactus(Material.cactus), 
	clay(Material.clay), 
	gourd(Material.gourd), 
	dragonEgg(Material.dragonEgg), 
	portal(Material.portal), 
	cake(Material.cake), 
	web(Material.web), 
	piston(Material.piston), 
	barrier(Material.barrier);
	
	private Material value;
	
	private MaterialResource(Material value){
		this.value = value;
	}
	
	public Material getValue(){
		return value;
	}
}
