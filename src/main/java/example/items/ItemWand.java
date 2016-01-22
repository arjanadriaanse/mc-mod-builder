package example.items;

import example.main.ModInformation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemWand extends Item {
	public static final String name = "jumpWand";
	
	public ItemWand(){
		GameRegistry.registerItem(this, name);
		setUnlocalizedName(ModInformation.MODID + "_" + name);
		setCreativeTab(CreativeTabs.tabCombat);
		setMaxStackSize(1);
		setMaxDamage(16);
	}
	
	@Override
	//Right click mob event
	public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase target){
		//If we are not on the client side, but on the server side
		if (!target.worldObj.isRemote){
			target.motionY = 2; //Shoot target in the air
		}
		stack.damageItem(1,player);
		return false;
	}
	
	public void renderItem(){
		RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
		renderItem.getItemModelMesher().register(this, 0, 
				new ModelResourceLocation(ModInformation.MODID + ":" + name, "inventory"));
	}
}
