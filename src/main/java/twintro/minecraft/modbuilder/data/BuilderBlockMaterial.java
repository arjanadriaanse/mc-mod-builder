package twintro.minecraft.modbuilder.data;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import twintro.minecraft.modbuilder.data.resources.blocks.BaseBlockResource;

/**
 * The default class for block materials.
 */
public class BuilderBlockMaterial extends Material{
	BaseBlockResource resource;
	
	public BuilderBlockMaterial(BaseBlockResource resource){
		super(MapColor.mapColorArray[resource.mapcolor!=null ? resource.mapcolor : 0]);
		this.resource=resource;
		if (resource.replaceable!=null ? resource.replaceable : false)
			setReplaceable();
		if (resource.requirestool!=null ? resource.requirestool : false)
			setRequiresTool();
		if (resource.mobility!=null) {
			if (resource.mobility==1){
				setNoPushMobility();
			}
			if (resource.mobility==2){
				setImmovableMobility();
			}
		}
	}
		
	
	@Override
	public boolean blocksMovement(){
		return resource.solid!=null ? resource.solid : super.blocksMovement();
	}
	
	@Override
	public boolean isOpaque(){
		return resource.opaque!=null ? resource.opaque : super.isOpaque();
	}
}

