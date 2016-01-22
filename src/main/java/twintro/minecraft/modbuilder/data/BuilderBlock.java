package twintro.minecraft.modbuilder.data;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import twintro.minecraft.modbuilder.data.resources.recipes.ItemStackResource;

/**
 * Base class for new {@Link Block}s
 */
public class BuilderBlock extends Block {
	/**
	 * The items the block will drop. The amount is just a minimal value and will be increased by dropincrease.
	 */
	List<ItemStackResource> items;
	boolean solid;
	boolean opaque;
	boolean cutout;

	public BuilderBlock(Material material, List<ItemStackResource> drops, boolean solid, boolean opaque, boolean cutout) {
		super(material);
		this.items=drops;
		this.solid=solid;
		this.opaque=opaque;
		this.cutout=cutout;
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
			Item item = ResourceConverter.getItemFromName(items.get(i).item);
			rtn.add(new ItemStack(item,amount !=null ? amount : 1+fortune,meta!=null ? meta : 0));
		}
		return rtn;
	}
	
	@Override
    public AxisAlignedBB getCollisionBoundingBox(World world, BlockPos pos, IBlockState state) {
        return solid ? super.getCollisionBoundingBox(world, pos, state) : null;
    }
	
	@SideOnly(Side.CLIENT)
	@Override
	public boolean isOpaqueCube(){
		return opaque;
	}
	
	@Override
	public EnumWorldBlockLayer getBlockLayer(){
		if (cutout)
			return EnumWorldBlockLayer.CUTOUT;
		else
			return super.getBlockLayer();
	}
}