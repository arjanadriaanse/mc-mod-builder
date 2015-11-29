package twintro.minecraft.modbuilder.data;

import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BuilderItem extends Item implements MesherRegisterable {

	public String model;

	private CreativeTabs[] tabs;

	public BuilderItem(String model, CreativeTabs[] tabs) {
		this.model = model;
		this.tabs = tabs;
	}

	@Override
	public CreativeTabs[] getCreativeTabs() {
		return tabs;
	}

	@Override
	public void register(ItemModelMesher mesher) {
		mesher.register(this, 0, new ModelResourceLocation(model, "inventory"));
	}

	@Override
	public void register(String path) {
		GameRegistry.registerItem(this, path);
	}
}
