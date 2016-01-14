package twintro.minecraft.modbuilder.editor.resources;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.ImageIcon;

import twintro.minecraft.modbuilder.editor.Editor;

public class MaterialResources {
	//TODO add everything, this is just an example
	public static final String[] vanillaBlocks = new String[]{
			"stone",
			"dirt",
			"grass",
			"glass"
	};
	public static final String[] vanillaBlockIds = new String[]{
			"minecraft:stone",
			"minecraft:dirt",
			"minecraft:grass",
			"minecraft:glass"
	};
	public static final String[] vanillaItems = new String[]{
			"string",
			"stick",
			"apple",
			"egg"
	};
	public static final String[] vanillaItemIds = new String[]{
			"minecraft:string",
			"minecraft:stick",
			"minecraft:apple",
			"minecraft:egg"
	};
	
	public static String getId(String material){
		for (int i = 0; i < vanillaBlocks.length; i++)
			if (vanillaBlocks[i].equals(material))
				return vanillaBlockIds[i];
		for (int i = 0; i < vanillaItems.length; i++)
			if (vanillaItems[i].equals(material))
				return vanillaItemIds[i];
		return null;
	}
	
	public static String getName(String material){
		String prefix = "";
		if (!material.startsWith("minecraft:") && material.contains("minecraft:")){
			prefix = material.split(" ")[0] + " ";
			material = material.split(" ")[1];
		}
		for (int i = 0; i < vanillaBlockIds.length; i++)
			if (vanillaBlockIds[i].equals(material))
				return prefix + vanillaBlocks[i];
		for (int i = 0; i < vanillaItemIds.length; i++)
			if (vanillaItemIds[i].equals(material))
				return prefix + vanillaItems[i];
		return prefix + material;
	}
	
	public static ImageIcon getImage(String material){
		if (material.startsWith("modbuilder:")){
			material = material.substring(11);
			try{
				if (Editor.ItemPanel.elements.containsKey(material)){
					return ItemElement.getFromName(material).getImage();
				}
				else{
					return BlockElement.getFromName(material).getImage();
				}
			}
			catch (Exception e){
				e.printStackTrace();
			}
		}
		else if (material.startsWith("minecraft:")){
			return null;
		}
		return null;
	}
	
	public static boolean isItem(String material){
		for (int i = 0; i < vanillaItemIds.length; i++)
			if (vanillaItemIds[i].equals(material))
				return true;
		if (material.startsWith("modbuilder:"))
			return Editor.ItemPanel.elements.containsKey(material.substring(11));
		return false;
	}
}
