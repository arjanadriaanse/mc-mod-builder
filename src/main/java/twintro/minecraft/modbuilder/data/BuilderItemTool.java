package twintro.minecraft.modbuilder.data;

import java.util.Set;

import net.minecraft.item.ItemTool;

public class BuilderItemTool extends ItemTool {

	public BuilderItemTool(float attackDamage, ToolMaterial material, Set effectiveBlocks) {
		super(attackDamage, material, effectiveBlocks);
	}

}
