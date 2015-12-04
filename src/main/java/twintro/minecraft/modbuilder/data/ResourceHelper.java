package twintro.minecraft.modbuilder.data;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import twintro.minecraft.modbuilder.data.resources.MaterialResource;
import twintro.minecraft.modbuilder.data.resources.TabResource;

public class ResourceHelper {
	public static final Map<MaterialResource, Material> materials = new HashMap<MaterialResource, Material>();
	public static final Map<TabResource, CreativeTabs> tabs = new HashMap<TabResource, CreativeTabs>();

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

	private static final Pattern camelPattern = Pattern.compile("(.)([A-Z])");

	public static void main(String[] args) {
		generateEnum(Material.class, null);
		generateEnum(CreativeTabs.class, "tab");
		generateMap("materials", MaterialResource.class, Material.class, null);
		generateMap("tabs", TabResource.class, CreativeTabs.class, "tab");
	}

	private static void generateEnum(Class c, String prefix) {
		for (Field f : c.getFields()) {
			if (f.getType() == c && Modifier.isPublic(f.getModifiers()) && Modifier.isStatic(f.getModifiers())) {
				String name = generateName(f.getName(), prefix);
				System.out.println(name + ",");
			}
		}
		System.out.println();
	}

	private static void generateMap(String v, Class<? extends Enum> e, Class c, String prefix) {
		for (Field f : c.getFields()) {
			if (f.getType() == c && Modifier.isPublic(f.getModifiers()) && Modifier.isStatic(f.getModifiers())) {
				String name = generateName(f.getName(), prefix);
				System.out.println(v + ".put(" + e.getSimpleName() + "." + name + ", " + c.getSimpleName() + "."
						+ f.getName() + ");");
			}
		}
		System.out.println();
	}

	private static String generateName(String name, String prefix) {
		if (prefix != null && name.startsWith(prefix))
			name = name.substring(prefix.length());

		Matcher matcher = camelPattern.matcher(name);
		name = matcher.replaceAll("$1_$2");
		return name.toLowerCase();
	}
}
