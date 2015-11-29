package twintro.minecraft.modbuilder.data;

import net.minecraft.client.renderer.ItemModelMesher;

public interface MesherRegisterable extends Registerable {
	public void register(ItemModelMesher mesher);
}
