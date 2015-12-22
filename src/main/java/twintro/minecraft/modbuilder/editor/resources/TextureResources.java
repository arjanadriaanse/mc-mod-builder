package twintro.minecraft.modbuilder.editor.resources;

import javax.swing.ImageIcon;

import twintro.minecraft.modbuilder.editor.generator.ResourcePackGenerator;

public class TextureResources {
	/**
	 * Gets the texture of an item or block.
	 * Use the id-name e.g. modbuilder:coolthing or minecraft:dirt.
	 */
	public static ImageIcon getImage(String name){
		if (name.startsWith("modbuilder:")){
			return new ImageIcon(ResourcePackGenerator.getURL(
					"assets/modbuilder/textures/" + name.substring(11) + ".png"));
		}
		else{
			return null;
		}
	}
}
