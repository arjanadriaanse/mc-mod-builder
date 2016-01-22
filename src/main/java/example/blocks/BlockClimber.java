package example.blocks;

import example.items.ItemWand;
import example.main.ModInformation;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockClimber extends Block {
	public static final String name = "climber";

	public BlockClimber() {
		super(Material.rock);
		GameRegistry.registerBlock(this, name);
		setUnlocalizedName(ModInformation.MODID + "_" + name);
		setCreativeTab(CreativeTabs.tabBlock);
		setHardness(2.0F);
		slipperiness = 1;
	}
	
	public void renderItem(){
		RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
		renderItem.getItemModelMesher().register(Item.getItemFromBlock(this), 0, 
				new ModelResourceLocation(ModInformation.MODID + ":" + name, "inventory"));
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumFacing side, float hitX, float hitY, float hitZ) {
		boolean returnValue = super.onBlockActivated(worldIn, pos, state, playerIn, side, hitX, hitY, hitZ);
		if (!worldIn.isRemote){
			ItemStack equippedItem = playerIn.getCurrentEquippedItem();
			if (equippedItem != null){
				if (equippedItem.getItem() instanceof ItemWand){
					Item climberItem = Item.getItemFromBlock(MyBlocks.climber);
					boolean creative = Minecraft.getMinecraft().playerController.getCurrentGameType().isCreative();
					if (playerIn.inventory.hasItem(climberItem) || creative){
						int y = 1;
						while (worldIn.getBlockState(pos.add(0, y, 0)).getBlock() instanceof BlockClimber){
							y++;
							if (pos.getY() + y > worldIn.getHeight()) return returnValue;
						}
						worldIn.setBlockState(pos.add(0, y, 0), MyBlocks.climber.getDefaultState());
						if (!creative){
							playerIn.inventory.consumeInventoryItem(Item.getItemFromBlock(MyBlocks.climber));
							playerIn.onUpdate();
						}
					}
				}
			}
		}
		return returnValue;
	}
}
