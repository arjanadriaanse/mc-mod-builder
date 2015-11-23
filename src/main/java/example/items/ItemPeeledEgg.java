package example.items;

import example.main.ModInformation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemPeeledEgg extends ItemFood {
	public static final String name = "peeledEgg";
	public static final int healAmount = 6;
	
	public ItemPeeledEgg(){
		super(healAmount, true);
		GameRegistry.registerItem(this, name);
		setUnlocalizedName(ModInformation.MODID + "_" + name);
		setCreativeTab(CreativeTabs.tabFood);
		setMaxStackSize(1);
	}
	
	public void renderItem(){
		RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
		renderItem.getItemModelMesher().register(this, 0, 
				new ModelResourceLocation(ModInformation.MODID + ":" + name, "inventory"));
	}
}
