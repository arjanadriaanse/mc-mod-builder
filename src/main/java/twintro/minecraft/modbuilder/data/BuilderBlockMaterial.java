package twintro.minecraft.modbuilder.data;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import twintro.minecraft.modbuilder.data.resources.blocks.BaseBlockResource;

/**
 * The default class for block materials.
 */
public class BuilderBlockMaterial extends Material{
	BaseBlockResource resource;
	
	public BuilderBlockMaterial(BaseBlockResource resource){
		super(MapColor.mapColorArray[resource.mapcolor!=null ? resource.mapcolor : 0]);
		this.resource=resource;
		if (resource.flammable!=null ? resource.flammable : false){
			setBurning();
			boolean a = getCanBurn();
			boolean b = isSolid();
		}
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
	public boolean isSolid(){
		return resource.solid!=null ? resource.solid : true;
	}
	
	@Override
	public boolean blocksMovement(){
		return resource.solid!=null ? resource.solid : true;
	}
	
	@Override
	public boolean isOpaque(){
		return resource.opaque!=null ? resource.opaque : false;
	}
	
	@Override
	public boolean getCanBurn(){
		return resource.flammable!=null ? resource.flammable : false;
	}
}

