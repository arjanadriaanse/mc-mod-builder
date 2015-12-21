package twintro.minecraft.modbuilder.editor.resources;

import javax.swing.ImageIcon;

import twintro.minecraft.modbuilder.data.resources.models.ItemModelResource;
import twintro.minecraft.modbuilder.editor.generator.ResourcePackGenerator;

public class InventoryElement extends Element {
	public ItemModelResource itemModel;

	@Override
	public ImageIcon getImage() {
		String texture = null;
		if (itemModel.textures.containsKey("all"))
			texture = itemModel.textures.get("all");
		else{
			Object[] textures = itemModel.textures.values().toArray();
			if (textures.length > 0) texture = (String) textures[0];
		}
		if (texture != null){
			if (texture.startsWith("modbuilder:")){
				return new ImageIcon(ResourcePackGenerator.getURL(
						"assets/modbuilder/textures/" + texture.substring(11) + ".png"));
			}
		}
		return null;
	}
}
