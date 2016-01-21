package twintro.minecraft.modbuilder.data;

import java.util.LinkedHashSet;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

/**
 * Base class for new {@link ItemTool}s.
 */
public class BuilderItemTool extends ItemTool {
	CreativeTabs[] tabs;
	Set<String> effectiveBlocks;

	/**
	 * 
	 * @param attackDamage
	 * 		-The damage dealt to any entity the player hits when holding this tool.
	 * @param material
	 * 		-The tool material this tool is made of. This contains properties such as mining speed and durability.
	 * @param effectiveBlocks
	 * 		-Set of all Block objects this tool is effective on.
	 */
	public BuilderItemTool(float attackDamage, ToolMaterial material, Set<String> effectiveBlocks, CreativeTabs[] tabs) {
		super(attackDamage, material, new LinkedHashSet());
		this.tabs = tabs;
		this.effectiveBlocks = effectiveBlocks;
	}
	
	@Override
	public CreativeTabs[] getCreativeTabs() {
		return tabs != null ? tabs : super.getCreativeTabs();
	}
	
	@Override
	public boolean canHarvestBlock(Block block){
		boolean b = super.canHarvestBlock(block);
		for(String s: effectiveBlocks){
			Block bl = Block.getBlockFromName(s);
			if(Block.getBlockFromName(s).equals(block))
				return true;
		}
		return false;
	}
}
