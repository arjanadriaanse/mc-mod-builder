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
			"Stone", "Granite", "Polished Granite", "Diorite", "Polished Diorite", "Andesite", "Polished Andesite", "Dirt", "Coarse Dirt", "Podzol", "Cobblestone", "Oak Wood Planks", "Spruce Wood Planks", "Birch Wood Planks", "Jungle Wood Planks", "Acacia Wood Planks", "Dark Oak Wood Planks", "Oak Sapling", "Spruce Sapling", "Birch Sapling", "Jungle Sapling", "Acacia Sapling", "Dark Oak Sapling", "Bedrock", "Gravel", "Gold Ore", "Iron Ore", "Coal Ore", "Oak Wood", "Spruce Wood", "Birch Wood", "Jungle Wood", "Acacia Wood", "Dark Oak Wood", "Acacia Wood", "Dark Oak Wood", "Oak Wood", "Sponge", "Wet Sponge", "Glass", "Lapis Lazuli Ore", "Lapis Lazuli Block", "Dispenser", "Sandstone", "Chiseled Sandstone", "Smooth Sandstone", "Note Block", "Powered Rail", "Detector Rail", "Cobweb", "Wool", "Orange Wool", "Magenta Wool", "Light Blue Wool", "Yellow Wool", "Lime Wool", "Pink Wool", "Gray Wool", "Light Gray Wool", "Cyan Wool", "Purple Wool", "Blue Wool", "Brown Wool", "Green Wool", "Red Wool", "Black Wool", "Block of Gold", "Block of Iron", "Bricks", "TNT", "Bookshelf", "Moss Stone", "Obsidian", "Torch", "Monster Spawner", "Oak Wood Stairs", "Diamond Ore", "Block of Diamond", "Crafting Table", "Farmland", "Furnace", "Furnace", "Ladder", "Rail", "Cobblestone Stairs", "Lever", "Stone Pressure Plate", "Wooden Pressure Plate", "Redstone Ore", "Redstone Torch", "Button", "Snow", "Ice", "Snow", "Clay", "Jukebox", "Oak Fence", "Spruce Fence", "Birch Fence", "Jungle Fence", "Dark Oak Fence", "Acacia Fence", "Pumpkin", "Netherrack", "Soul Sand", "Glowstone", "Jack o'Lantern", "Wooden Trapdoor", "Stone Monster Egg", "Cobblestone Monster Egg", "Stone Brick Monster Egg", "Mossy Stone Brick Monster Egg", "Cracked Stone Brick Monster Egg", "Chiseled Stone Brick Monster Egg", "Stone Bricks", "Mossy Stone Bricks", "Cracked Stone Bricks", "Chiseled Stone Bricks", "Mushroom", "Mushroom", "Iron Bars", "Glass Pane", "Melon", "Vines", "Oak Fence Gate", "Spruce Fence Gate", "Birch Fence Gate", "Jungle Fence Gate", "Dark Oak Fence Gate", "Acacia Fence Gate", "Brick Stairs", "Stone Brick Stairs", "Lily Pad", "Nether Brick", "Nether Brick Fence", "Nether Brick Stairs", "Enchantment Table", "End Portal", "End Stone", "Dragon Egg", "Redstone Lamp", "Sandstone Stairs", "Emerald Ore", "Ender Chest", "Block of Emerald", "Spruce Wood Stairs", "Birch Wood Stairs", "Jungle Wood Stairs", "Command Block", "Cobblestone Wall", "Mossy Cobblestone Wall", "Button", "Anvil", "Slightly Damaged Anvil", "Very Damaged Anvil", "Trapped Chest", "Weighted Pressure Plate (Light)", "Weighted Pressure Plate (Heavy)", "Block of Redstone", "Nether Quartz Ore", "Block of Quartz", "Chiseled Quartz Block", "Pillar Quartz Block", "Quartz Stairs", "Activator Rail", "Dropper", "White Stained Clay", "Orange Stained Clay", "Magenta Stained Clay", "Light Blue Stained Clay", "Yellow Stained Clay", "Lime Stained Clay", "Pink Stained Clay", "Gray Stained Clay", "Light Gray Stained Clay", "Cyan Stained Clay", "Purple Stained Clay", "Blue Stained Clay", "Brown Stained Clay", "Green Stained Clay", "Red Stained Clay", "Black Stained Clay", "Barrier", "Iron Trapdoor", "Hay Bale", "Carpet", "Orange Carpet", "Magenta Carpet", "Light Blue Carpet", "Yellow Carpet", "Lime Carpet", "Pink Carpet", "Gray Carpet", "Light Gray Carpet", "Cyan Carpet", "Purple Carpet", "Blue Carpet", "Brown Carpet", "Green Carpet", "Red Carpet", "Black Carpet", "Hardened Clay", "Block of Coal", "Packed Ice", "Acacia Wood Stairs", "Dark Oak Wood Stairs", "Slime Block", "Prismarine", "Prismarine Bricks", "Dark Prismarine", "Sea Lantern", "Red Sandstone", "Chiseled Red Sandstone", "Smooth Red Sandstone", "Red Sandstone Stairs", 
	};
	public static final String[] vanillaBlockIds = new String[]{
			"minecraft:stone#0", "minecraft:stone#1", "minecraft:stone#2", "minecraft:stone#3", "minecraft:stone#4", "minecraft:stone#5", "minecraft:stone#6", "minecraft:dirt#0", "minecraft:dirt#1", "minecraft:dirt#2", "minecraft:cobblestone", "minecraft:planks#0", "minecraft:planks#1", "minecraft:planks#2", "minecraft:planks#3", "minecraft:planks#4", "minecraft:planks#5", "minecraft:sapling#0", "minecraft:sapling#1", "minecraft:sapling#2", "minecraft:sapling#3", "minecraft:sapling#4", "minecraft:sapling#5", "minecraft:bedrock", "minecraft:gravel", "minecraft:gold_ore", "minecraft:iron_ore", "minecraft:coal_ore", "minecraft:log#0", "minecraft:log#1", "minecraft:log#2", "minecraft:log#3", "minecraft:log#4", "minecraft:log#5", "minecraft:log2#0", "minecraft:log2#1", "minecraft:log2#2", "minecraft:sponge#0", "minecraft:sponge#1", "minecraft:glass", "minecraft:lapis_ore", "minecraft:lapis_block", "minecraft:dispenser", "minecraft:sandstone#0", "minecraft:sandstone#1", "minecraft:sandstone#2", "minecraft:noteblock", "minecraft:golden_rail", "minecraft:detector_rail", "minecraft:web", "minecraft:wool#0", "minecraft:wool#1", "minecraft:wool#2", "minecraft:wool#3", "minecraft:wool#4", "minecraft:wool#5", "minecraft:wool#6", "minecraft:wool#7", "minecraft:wool#8", "minecraft:wool#9", "minecraft:wool#10", "minecraft:wool#11", "minecraft:wool#12", "minecraft:wool#13", "minecraft:wool#14", "minecraft:wool#15", "minecraft:gold_block", "minecraft:iron_block", "minecraft:brick_block", "minecraft:tnt", "minecraft:bookshelf", "minecraft:mossy_cobblestone", "minecraft:obsidian", "minecraft:torch", "minecraft:mob_spawner", "minecraft:oak_stairs", "minecraft:diamond_ore", "minecraft:diamond_block", "minecraft:crafting_table", "minecraft:farmland", "minecraft:furnace", "minecraft:lit_furnace", "minecraft:ladder", "minecraft:rail", "minecraft:stone_stairs", "minecraft:lever", "minecraft:stone_pressure_plate", "minecraft:wooden_pressure_plate", "minecraft:redstone_ore", "minecraft:redstone_torch", "minecraft:stone_button", "minecraft:snow_layer", "minecraft:ice", "minecraft:snow", "minecraft:clay", "minecraft:jukebox", "minecraft:oak_fence", "minecraft:spruce_fence", "minecraft:birch_fence", "minecraft:jungle_fence", "minecraft:dark_oak_fence", "minecraft:acacia_fence", "minecraft:pumpkin", "minecraft:netherrack", "minecraft:soul_sand", "minecraft:glowstone", "minecraft:lit_pumpkin", "minecraft:trapdoor", "minecraft:monster_egg#0", "minecraft:monster_egg#1", "minecraft:monster_egg#2", "minecraft:monster_egg#3", "minecraft:monster_egg#4", "minecraft:monster_egg#5", "minecraft:stonebrick#0", "minecraft:stonebrick#1", "minecraft:stonebrick#2", "minecraft:stonebrick#3", "minecraft:brown_mushroom_block", "minecraft:red_mushroom_block", "minecraft:iron_bars", "minecraft:glass_pane", "minecraft:melon_block", "minecraft:vine", "minecraft:oak_fence_gate", "minecraft:spruce_fence_gate", "minecraft:birch_fence_gate", "minecraft:jungle_fence_gate", "minecraft:dark_oak_fence_gate", "minecraft:acacia_fence_gate", "minecraft:brick_stairs", "minecraft:stone_brick_stairs", "minecraft:waterlily", "minecraft:nether_brick", "minecraft:nether_brick_fence", "minecraft:nether_brick_stairs", "minecraft:enchanting_table", "minecraft:end_portal_frame", "minecraft:end_stone", "minecraft:dragon_egg", "minecraft:redstone_lamp", "minecraft:sandstone_stairs", "minecraft:emerald_ore", "minecraft:ender_chest", "minecraft:emerald_block", "minecraft:spruce_stairs", "minecraft:birch_stairs", "minecraft:jungle_stairs", "minecraft:command_block", "minecraft:cobblestone_wall#0", "minecraft:cobblestone_wall#1", "minecraft:wooden_button", "minecraft:anvil#0", "minecraft:anvil#1", "minecraft:anvil#2", "minecraft:trapped_chest", "minecraft:light_weighted_pressure_plate", "minecraft:heavy_weighted_pressure_plate", "minecraft:redstone_block", "minecraft:quartz_ore", "minecraft:quartz_block#0", "minecraft:quartz_block#1", "minecraft:quartz_block#2", "minecraft:quartz_stairs", "minecraft:activator_rail", "minecraft:dropper", "minecraft:stained_hardened_clay#0", "minecraft:stained_hardened_clay#1", "minecraft:stained_hardened_clay#2", "minecraft:stained_hardened_clay#3", "minecraft:stained_hardened_clay#4", "minecraft:stained_hardened_clay#5", "minecraft:stained_hardened_clay#6", "minecraft:stained_hardened_clay#7", "minecraft:stained_hardened_clay#8", "minecraft:stained_hardened_clay#9", "minecraft:stained_hardened_clay#10", "minecraft:stained_hardened_clay#11", "minecraft:stained_hardened_clay#12", "minecraft:stained_hardened_clay#13", "minecraft:stained_hardened_clay#14", "minecraft:stained_hardened_clay#15", "minecraft:barrier", "minecraft:iron_trapdoor", "minecraft:hay_block", "minecraft:carpet#0", "minecraft:carpet#1", "minecraft:carpet#2", "minecraft:carpet#3", "minecraft:carpet#4", "minecraft:carpet#5", "minecraft:carpet#6", "minecraft:carpet#7", "minecraft:carpet#8", "minecraft:carpet#9", "minecraft:carpet#10", "minecraft:carpet#11", "minecraft:carpet#12", "minecraft:carpet#13", "minecraft:carpet#14", "minecraft:carpet#15", "minecraft:hardened_clay", "minecraft:coal_block", "minecraft:packed_ice", "minecraft:acacia_stairs", "minecraft:dark_oak_stairs", "minecraft:slime_block", "minecraft:prismarine#0", "minecraft:prismarine#1", "minecraft:prismarine#2", "minecraft:sea_lantern", "minecraft:red_sandstone#0", "minecraft:red_sandstone#1", "minecraft:red_sandstone#2", "minecraft:red_sandstone_stairs", 
	};
	public static final String[] vanillaItems = new String[]{
			"Iron Shovel", "Iron Pickaxe", "Iron Axe", "Flint and Steel", "Apple", "Arrow", "Coal", "Charcoal", "Diamond", "Iron Ingot", "Gold Ingot", "Iron Sword", "Wooden Sword", "Wooden Shovel", "Wooden Pickaxe", "Wooden Axe", "Stone Sword", "Stone Shovel", "Stone Pickaxe", "Stone Axe", "Diamond Sword", "Diamond Shovel", "Diamond Pickaxe", "Diamond Axe", "Stick", "Bowl", "Mushroom Stew", "Golden Sword", "Golden Shovel", "Golden Pickaxe", "Golden Axe", "String", "Feather", "Gunpowder", "Wooden Hoe", "Stone Hoe", "Iron Hoe", "Diamond Hoe", "Golden Hoe", "Seeds", "Wheat", "Bread", "Flint", "Raw Porkchop", "Cooked Porkchop", "Painting", "Golden Apple", "Sign", "Oak Door", "Spruce Door", "Birch Door", "Jungle Door", "Acacia Door", "Dark Oak Door", "Bucket", "Water Bucket", "Lava Bucket", "Minecart", "Saddle", "Iron Door", "Redstone", "Snowball", "Boat", "Leather", "Milk", "Brick", "Clay", "Sugar Canes", "Paper", "Book", "Slimeball", "Minecart with Chest", "Minecart with Furnace", "Egg", "Compass", "Clock", "Glowstone Dust", "Raw Fish", "Raw Salmon", "Clownfish", "Pufferfish", "Cooked Fish", "Cooked Salmon", "Clownfish", "Pufferfish", "Ink Sac", "Rose Red", "Cactus Green", "Cocoa Beans", "Lapis Lazuli", "Purple Dye", "Cyan Dye", "Light Gray Dye", "Gray Dye", "Pink Dye", "Lime Dye", "Dandelion Yellow", "Light Blue Dye", "Magenta Dye", "Orange Dye", "Bone Meal", "Bone", "Sugar", "Cake", "Bed", "Redstone Repeater", "Cookie", "Melon", "Pumpkin Seeds", "Melon Seeds", "Raw Beef", "Steak", "Raw Chicken", "Cooked Chicken", "Raw Mutton", "Cooked Mutton", "Raw Rabbit", "Cooked Rabbit", "Rabbit Stew", "Rabbit's Foot", "Rabbit Hide", "Rotten Flesh", "Ender Pearl", "Blaze Rod", "Ghast Tear", "Gold Nugget", "Nether Wart", "Glass Bottle", "Spider Eye", "Fermented Spider Eye", "Blaze Powder", "Magma Cream", "Brewing Stand", "Cauldron", "Eye of Ender", "Glistering Melon", "Spawn", "Spawn Item", "Spawn Experience Orb", "Bottle o' Enchanting", "Fire Charge", "Book and Quill", "Written Book", "Emerald", "Item Frame", "Flower Pot", "Carrot", "Potato", "Baked Potato", "Poisonous Potato", "Golden Carrot", "Skeleton Skull", "Wither Skeleton Skull", "Zombie Head", "Head", "Creeper Head", "Carrot on a Stick", "Nether Star", "Pumpkin Pie", "Firework Rocket", "Firework Star", "Redstone Comparator", "Nether Brick", "Nether Quartz", "Minecart with TNT", "Minecart with Hopper", "Iron Horse Armor", "Gold Horse Armor", "Diamond Horse Armor", "Lead", "Name Tag", "Minecart with Command Block", "Music Disc", "Music Disc", "Music Disc", "Music Disc", "Music Disc", "Music Disc", "Music Disc", "Music Disc", "Music Disc", "Music Disc", "Music Disc", "Music Disc", "Prismarine Shard", "Prismarine Crystals", "Black Banner", "Red Banner", "Green Banner", "Brown Banner", "Blue Banner", "Purple Banner", "Cyan Banner", "Light Gray Banner", "Gray Banner", "Pink Banner", "Lime Banner", "Yellow Banner", "Light Blue Banner", "Magenta Banner", "Orange Banner", "White Banner", 
	};
	public static final String[] vanillaItemIds = new String[]{
			"minecraft:iron_shovel", "minecraft:iron_pickaxe", "minecraft:iron_axe", "minecraft:flint_and_steel", "minecraft:apple", "minecraft:arrow", "minecraft:coal#0", "minecraft:coal#1", "minecraft:diamond", "minecraft:iron_ingot", "minecraft:gold_ingot", "minecraft:iron_sword", "minecraft:wooden_sword", "minecraft:wooden_shovel", "minecraft:wooden_pickaxe", "minecraft:wooden_axe", "minecraft:stone_sword", "minecraft:stone_shovel", "minecraft:stone_pickaxe", "minecraft:stone_axe", "minecraft:diamond_sword", "minecraft:diamond_shovel", "minecraft:diamond_pickaxe", "minecraft:diamond_axe", "minecraft:stick", "minecraft:bowl", "minecraft:mushroom_stew", "minecraft:golden_sword", "minecraft:golden_shovel", "minecraft:golden_pickaxe", "minecraft:golden_axe", "minecraft:string", "minecraft:feather", "minecraft:gunpowder", "minecraft:wooden_hoe", "minecraft:stone_hoe", "minecraft:iron_hoe", "minecraft:diamond_hoe", "minecraft:golden_hoe", "minecraft:wheat_seeds", "minecraft:wheat", "minecraft:bread", "minecraft:flint", "minecraft:porkchop", "minecraft:cooked_porkchop", "minecraft:painting", "minecraft:golden_apple", "minecraft:sign", "minecraft:oak_door", "minecraft:spruce_door", "minecraft:birch_door", "minecraft:jungle_door", "minecraft:acacia_door", "minecraft:dark_oak_door", "minecraft:bucket", "minecraft:water_bucket", "minecraft:lava_bucket", "minecraft:minecart", "minecraft:saddle", "minecraft:iron_door", "minecraft:redstone", "minecraft:snowball", "minecraft:boat", "minecraft:leather", "minecraft:milk_bucket", "minecraft:brick", "minecraft:clay_ball", "minecraft:reeds", "minecraft:paper", "minecraft:book", "minecraft:slime_ball", "minecraft:chest_minecart", "minecraft:furnace_minecart", "minecraft:egg", "minecraft:compass", "minecraft:clock", "minecraft:glowstone_dust", "minecraft:fish#0", "minecraft:fish#1", "minecraft:fish#2", "minecraft:fish#3", "minecraft:cooked_fish#0", "minecraft:cooked_fish#1", "minecraft:cooked_fish#2", "minecraft:cooked_fish#3", "minecraft:dye#0", "minecraft:dye#1", "minecraft:dye#2", "minecraft:dye#3", "minecraft:dye#4", "minecraft:dye#5", "minecraft:dye#6", "minecraft:dye#7", "minecraft:dye#8", "minecraft:dye#9", "minecraft:dye#10", "minecraft:dye#11", "minecraft:dye#12", "minecraft:dye#13", "minecraft:dye#14", "minecraft:dye#15", "minecraft:bone", "minecraft:sugar", "minecraft:cake", "minecraft:bed", "minecraft:repeater", "minecraft:cookie", "minecraft:melon", "minecraft:pumpkin_seeds", "minecraft:melon_seeds", "minecraft:beef", "minecraft:cooked_beef", "minecraft:chicken", "minecraft:cooked_chicken", "minecraft:mutton", "minecraft:cooked_mutton", "minecraft:rabbit", "minecraft:cooked_rabbit", "minecraft:rabbit_stew", "minecraft:rabbit_foot", "minecraft:rabbit_hide", "minecraft:rotten_flesh", "minecraft:ender_pearl", "minecraft:blaze_rod", "minecraft:ghast_tear", "minecraft:gold_nugget", "minecraft:nether_wart", "minecraft:glass_bottle", "minecraft:spider_eye", "minecraft:fermented_spider_eye", "minecraft:blaze_powder", "minecraft:magma_cream", "minecraft:brewing_stand", "minecraft:cauldron", "minecraft:ender_eye", "minecraft:speckled_melon", "minecraft:spawn_egg#0", "minecraft:spawn_egg#1", "minecraft:spawn_egg#2", "minecraft:experience_bottle", "minecraft:fire_charge", "minecraft:writable_book", "minecraft:written_book", "minecraft:emerald", "minecraft:item_frame", "minecraft:flower_pot", "minecraft:carrot", "minecraft:potato", "minecraft:baked_potato", "minecraft:poisonous_potato", "minecraft:golden_carrot", "minecraft:skull#0", "minecraft:skull#1", "minecraft:skull#2", "minecraft:skull#3", "minecraft:skull#4", "minecraft:carrot_on_a_stick", "minecraft:nether_star", "minecraft:pumpkin_pie", "minecraft:fireworks", "minecraft:firework_charge", "minecraft:comparator", "minecraft:netherbrick", "minecraft:quartz", "minecraft:tnt_minecart", "minecraft:hopper_minecart", "minecraft:iron_horse_armor", "minecraft:golden_horse_armor", "minecraft:diamond_horse_armor", "minecraft:lead", "minecraft:name_tag", "minecraft:command_block_minecart", "minecraft:record_13", "minecraft:record_cat", "minecraft:record_blocks", "minecraft:record_chirp", "minecraft:record_far", "minecraft:record_mall", "minecraft:record_mellohi", "minecraft:record_stal", "minecraft:record_strad", "minecraft:record_ward", "minecraft:record_11", "minecraft:record_wait", "minecraft:prismarine_shard", "minecraft:prismarine_crystals", "minecraft:banner#0", "minecraft:banner#1", "minecraft:banner#2", "minecraft:banner#3", "minecraft:banner#4", "minecraft:banner#5", "minecraft:banner#6", "minecraft:banner#7", "minecraft:banner#8", "minecraft:banner#9", "minecraft:banner#10", "minecraft:banner#11", "minecraft:banner#12", "minecraft:banner#13", "minecraft:banner#14", "minecraft:banner#15", 
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
	
	public static String simplifyItemStackName(String material){
		material = material.replace("modbuilder:", "");
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
				if (Editor.getItemList().containsKey(material)){
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
			return Editor.getItemList().containsKey(material.substring(11));
		return false;
	}
}
