package example.blocks;

import example.main.ModInformation;
import net.minecraft.block.Block;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ColorBlock extends Block {
	public static final String name = "colorblock";
	public static final PropertyEnum color = PropertyEnum.create("color", ColorBlock.EnumColor.class);

	public ColorBlock() {
		super(Material.rock);
		GameRegistry.registerBlock(this, name);
		setUnlocalizedName(ModInformation.MODID + "_" + name);
		setCreativeTab(CreativeTabs.tabBlock);
		setHardness(1.0F);
		setDefaultState(blockState.getBaseState().withProperty(color, EnumColor.BLACK));
	}

	public void renderItem(){
		RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
		renderItem.getItemModelMesher().register(Item.getItemFromBlock(this), 0, 
				new ModelResourceLocation(ModInformation.MODID + ":" + name, "inventory"));
	}
	
	public enum EnumColor implements IStringSerializable {
	    WHITE(0, "white"),
	    BLACK(1, "black");

	    private int ID;
	    private String name;
	    
	    private EnumColor(int ID, String name) {
	        this.ID = ID;
	        this.name = name;
	    }
	    
	    @Override
	    public String getName() {
	        return name;
	    }

	    public int getID() {
	        return ID;
	    }
	    
	    @Override
	    public String toString() {
	        return getName();
	    }
	}
		
	@Override
	protected BlockState createBlockState() {
	    return new BlockState(this, new IProperty[] { color });
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
	    return getDefaultState().withProperty(color, meta == 0 ? EnumColor.BLACK : EnumColor.WHITE);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
	    EnumColor c = (EnumColor) state.getValue(color);
	    return c.getID();
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
			
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumFacing side, float hitX, float hitY, float hitZ) {
		boolean returnValue = super.onBlockActivated(worldIn, pos, state, playerIn, side, hitX, hitY, hitZ);
		if(state.getValue(color)==EnumColor.BLACK)
			worldIn.setBlockState(pos, worldIn.getBlockState(pos) .withProperty(color, EnumColor.WHITE));
		if(state.getValue(color)==EnumColor.WHITE)
			worldIn.setBlockState(pos, worldIn.getBlockState(pos) .withProperty(color, EnumColor.BLACK));
		return returnValue;
	}
}
