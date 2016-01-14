package twintro.minecraft.modbuilder.editor.resources;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.ImageIcon;

public class VanillaElements {
	public static Map<String, ImageIcon> customItems;

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
	
	/**
	 * @param material
	 * @return true if the string is in vanillaItemIds
	 */
	public static boolean isItem(String material){
		for (int i = 0; i < vanillaItemIds.length; i++)
			if (vanillaItemIds[i].equals(material))
				return true;
		if (material.startsWith("modbuilder:"))
			return customItems.containsKey(material.substring(11));
		return false;
	}
}
