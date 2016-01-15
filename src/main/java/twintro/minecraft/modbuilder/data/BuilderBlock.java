package twintro.minecraft.modbuilder.data;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import twintro.minecraft.modbuilder.data.resources.recipes.ItemStackResource;

/**
 * Base class for new {@Link Block}s
 */
public class BuilderBlock extends Block {
	/**
	 * The items the block will drop. The amount is just a minimal value and will be increased by dropincrease.
	 */
	List<ItemStackResource> items;

	public BuilderBlock(Material material, List<ItemStackResource> drops) {
		super(material);
		this.items=drops;
	}
	
	@Override
	public List<ItemStack> getDrops(IBlockAccess access, BlockPos pos, IBlockState state, int fortune){
		List<ItemStack> rtn = new LinkedList<ItemStack>();
		for(int i=0;i<items.size();i++){
			Integer amount=items.get(i).amount;
			Integer amountincrease=items.get(i).amountincrease;
			if (amount!=null && items.get(i).amountincrease!=null)
				amount +=RANDOM.nextInt(1+items.get(i).amountincrease)+fortune;
			Integer meta=items.get(i).meta;
			String s = items.get(i).item;
			Item item = Item.getByNameOrId(items.get(i).item);
			rtn.add(new ItemStack(item,amount !=null ? amount : 1+fortune,meta!=null ? meta : getMetaFromState(state)));
		}
		return rtn;
	}
}