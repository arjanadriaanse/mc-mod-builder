package twintro.minecraft.modbuilder.editor.resources;

import javax.swing.ImageIcon;

import twintro.minecraft.modbuilder.editor.generator.ResourcePackIO;

public class TextureResources {
	public static ImageIcon getImage(String name){
		if (name.startsWith("modbuilder:")){
			return new ImageIcon(ResourcePackIO.getURL("assets/modbuilder/textures/" + name.substring(11) + ".png"));
		}
		else{
			return null;
		}
	}
}
