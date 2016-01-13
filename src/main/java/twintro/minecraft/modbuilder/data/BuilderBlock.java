package twintro.minecraft.modbuilder.data;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;

/**
 * Base class for new {@Link Block}s
 */
public class BuilderBlock extends Block {

	public BuilderBlock(Material material) {
		super(material);
	}
}
