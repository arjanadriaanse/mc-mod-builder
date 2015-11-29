package twintro.minecraft.modbuilder.data;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BuilderBlock extends Block implements MesherRegisterable {

	public String model;

	public BuilderBlock(Material material, String model) {
		super(material);
		this.model = model;
	}

	@Override
	public void register(ItemModelMesher mesher) {
		mesher.register(Item.getItemFromBlock(this), 0, new ModelResourceLocation(model, "inventory"));
	}

	@Override
	public void register(String path) {
		GameRegistry.registerBlock(this, path);
	}
}
