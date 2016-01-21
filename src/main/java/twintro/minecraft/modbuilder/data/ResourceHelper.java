package twintro.minecraft.modbuilder.data;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import twintro.minecraft.modbuilder.data.resources.MaterialResource;
import twintro.minecraft.modbuilder.data.resources.TabResource;

/**
 * Contains maps for converting enum resources to objects and methods to
 * generate the enums and maps.
 *
 */
public class ResourceHelper {
	/**
	 * Contains a {@link Material} instance for each
	 * {@link MaterialResource}.
	 */
	public static final Map<MaterialResource, Material> materials = new HashMap<MaterialResource, Material>();

	/**
	 * Contains a {@link CreativeTabs} instance for each
	 * {@link TabResource}.
	 */
	public static final Map<TabResource, CreativeTabs> tabs = new HashMap<TabResource, CreativeTabs>();

	/**
	 * Contains an unlocalized name for each
	 * {@link Item} in vanilla minecraft.
	 */
	public static final Map<String, String> items = new HashMap<String, String>();

	/**
	 * Contains an unlocalized name for each
	 * {@link Block} in vanilla minecraft.
	 */
	public static final Map<String, String> blocks = new HashMap<String, String>();

	static {
		materials.put(MaterialResource.air, Material.air);
		materials.put(MaterialResource.grass, Material.grass);
		materials.put(MaterialResource.ground, Material.ground);
		materials.put(MaterialResource.wood, Material.wood);
		materials.put(MaterialResource.rock, Material.rock);
		materials.put(MaterialResource.iron, Material.iron);
		materials.put(MaterialResource.anvil, Material.anvil);
		materials.put(MaterialResource.water, Material.water);
		materials.put(MaterialResource.lava, Material.lava);
		materials.put(MaterialResource.leaves, Material.leaves);
		materials.put(MaterialResource.plants, Material.plants);
		materials.put(MaterialResource.vine, Material.vine);
		materials.put(MaterialResource.sponge, Material.sponge);
		materials.put(MaterialResource.cloth, Material.cloth);
		materials.put(MaterialResource.fire, Material.fire);
		materials.put(MaterialResource.sand, Material.sand);
		materials.put(MaterialResource.circuits, Material.circuits);
		materials.put(MaterialResource.carpet, Material.carpet);
		materials.put(MaterialResource.glass, Material.glass);
		materials.put(MaterialResource.redstone_light, Material.redstoneLight);
		materials.put(MaterialResource.tnt, Material.tnt);
		materials.put(MaterialResource.coral, Material.coral);
		materials.put(MaterialResource.ice, Material.ice);
		materials.put(MaterialResource.packed_ice, Material.packedIce);
		materials.put(MaterialResource.snow, Material.snow);
		materials.put(MaterialResource.crafted_snow, Material.craftedSnow);
		materials.put(MaterialResource.cactus, Material.cactus);
		materials.put(MaterialResource.clay, Material.clay);
		materials.put(MaterialResource.gourd, Material.gourd);
		materials.put(MaterialResource.dragon_egg, Material.dragonEgg);
		materials.put(MaterialResource.portal, Material.portal);
		materials.put(MaterialResource.cake, Material.cake);
		materials.put(MaterialResource.web, Material.web);
		materials.put(MaterialResource.piston, Material.piston);
		materials.put(MaterialResource.barrier, Material.barrier);

		tabs.put(TabResource.block, CreativeTabs.tabBlock);
		tabs.put(TabResource.decorations, CreativeTabs.tabDecorations);
		tabs.put(TabResource.redstone, CreativeTabs.tabRedstone);
		tabs.put(TabResource.transport, CreativeTabs.tabTransport);
		tabs.put(TabResource.misc, CreativeTabs.tabMisc);
		tabs.put(TabResource.all_search, CreativeTabs.tabAllSearch);
		tabs.put(TabResource.food, CreativeTabs.tabFood);
		tabs.put(TabResource.tools, CreativeTabs.tabTools);
		tabs.put(TabResource.combat, CreativeTabs.tabCombat);
		tabs.put(TabResource.brewing, CreativeTabs.tabBrewing);
		tabs.put(TabResource.materials, CreativeTabs.tabMaterials);
		tabs.put(TabResource.inventory, CreativeTabs.tabInventory);
	}

	/**
	 * Generates code for populating the {@link MaterialResource} and
	 * {@link TabResource} enums and their maps.
	 */
	public static void main(String[] args) {
		generateEnum(Material.class, null);
		generateEnum(CreativeTabs.class, "tab");
		generateMap("materials", MaterialResource.class, Material.class, null);
		generateMap("tabs", TabResource.class, CreativeTabs.class, "tab");
	}

	/**
	 * Generates code for populating an enum with names of the public static
	 * final fields of a specific type.
	 * 
	 * @param c
	 *            - the class to get the fields from
	 * @param prefix
	 *            - an optional prefix to filter from field names
	 */
	public static void generateEnum(Class c, String prefix) {
		for (Field f : c.getFields()) {
			if (f.getType() == c && Modifier.isPublic(f.getModifiers()) && Modifier.isStatic(f.getModifiers())) {
				String name = convertName(f.getName(), prefix);
				System.out.println(name + ",");
			}
		}
		System.out.println();
	}

	/**
	 * Generates code for populating a map with enums and the corresponding
	 * objects.
	 * 
	 * @param v
	 *            - the variable name of the map
	 * @param e
	 *            - the enum to use as key
	 * @param c
	 *            - the class to get the fields from
	 * @param prefix
	 *            - an optional prefix to filter from field names
	 */
	public static void generateMap(String v, Class<? extends Enum> e, Class c, String prefix) {
		for (Field f : c.getFields()) {
			if (f.getType() == c && Modifier.isPublic(f.getModifiers()) && Modifier.isStatic(f.getModifiers())) {
				String name = convertName(f.getName(), prefix);
				System.out.println(v + ".put(" + e.getSimpleName() + "." + name + ", " + c.getSimpleName() + "."
						+ f.getName() + ");");
			}
		}
		System.out.println();
	}

	/**
	 * Converts a camel case name to a lower case name with underscores between
	 * words.
	 * 
	 * @param name
	 *            - the name to convert
	 * @param prefix
	 *            - an optional prefix to filter from the name
	 */
	public static String convertName(String name, String prefix) {
		if (prefix != null && name.matches(prefix + "[A-Z].*"))
			name = name.substring(prefix.length());

		return name.replaceAll("(.)([A-Z])", "$1_$2").toLowerCase();
	}
	
	public static String generateSprites() {
		return null;
	}
}
