package twintro.minecraft.modbuilder.editor.resources;

import java.util.Map;

import javax.swing.ImageIcon;

import twintro.minecraft.modbuilder.data.resources.models.ItemModelResource;
import twintro.minecraft.modbuilder.editor.generator.ResourcePackGenerator;

public abstract class InventoryElement extends Element {
	public ItemModelResource itemModel;
	
	protected ImageIcon getImage(Map<String, String> textures, String defaultTexture){
		String texture = null;
		if (textures.containsKey(defaultTexture))
			texture = textures.get(defaultTexture);
		else{
			Object[] textureNames = textures.values().toArray();
			if (textureNames.length > 0) texture = (String) textureNames[0];
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
