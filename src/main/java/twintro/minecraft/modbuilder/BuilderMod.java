package twintro.minecraft.modbuilder;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.ResourcePackRepository;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import twintro.minecraft.modbuilder.data.MetadataSection;
import twintro.minecraft.modbuilder.data.MetadataSerializer;
import twintro.minecraft.modbuilder.data.RecipeRegistry;
import twintro.minecraft.modbuilder.data.ResourceConverter;
import twintro.minecraft.modbuilder.data.ResourceHelper;
import twintro.minecraft.modbuilder.data.resources.BaseItemResource;
import twintro.minecraft.modbuilder.data.resources.BaseRecipe;
import twintro.minecraft.modbuilder.data.resources.BaseBlockResource;
import twintro.minecraft.modbuilder.data.resources.ResourceDeserializer;

@Mod(modid = BuilderMod.MODID, version = BuilderMod.VERSION, guiFactory = "twintro.minecraft.modbuilder.BuilderModGuiFactory")
public class BuilderMod {
	public static final String MODID = "modbuilder";
	public static final String VERSION = "0.1";

	private static Configuration config;

	public static Configuration getConfig() {
		return config;
	}

	private Map<Item, String> itemModels = new LinkedHashMap<Item, String>();
	private Map<Block, String> blockModels = new LinkedHashMap<Block, String>();

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		ResourceHelper.init();

		importResources(Minecraft.getMinecraft().getResourceManager());

		config = new Configuration(event.getSuggestedConfigurationFile());
		syncConfig();
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		FMLCommonHandler.instance().bus().register(this);

		ItemModelMesher mesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();
		for (Entry<Item, String> entry : itemModels.entrySet())
			mesher.register(entry.getKey(), 0, new ModelResourceLocation(entry.getValue(), "inventory"));
		for (Entry<Block, String> entry : blockModels.entrySet())
			mesher.register(Item.getItemFromBlock(entry.getKey()), 0,
					new ModelResourceLocation(entry.getValue(), "inventory"));
	}

	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
		if (event.modID.equals(BuilderMod.MODID))
			syncConfig();
	}

	private void importResources(IResourceManager resourceManager) {
		List entries = Minecraft.getMinecraft().getResourcePackRepository().getRepositoryEntries();
		Iterator iterator = entries.iterator();
		while (iterator.hasNext()) {
			ResourcePackRepository.Entry entry = (ResourcePackRepository.Entry) iterator.next();
			try {
				MetadataSection data = (MetadataSection) entry.getResourcePack()
						.getPackMetadata(new MetadataSerializer(), "modbuilder");
				if (data != null)
					importResources(resourceManager, data);
			} catch (IOException e) {
				// ignore
			}
		}
	}

	private void importResources(IResourceManager resourceManager, MetadataSection data) {
		ResourceDeserializer recipeDeserializer = new ResourceDeserializer();
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(BaseItemResource.class, recipeDeserializer);
		builder.registerTypeAdapter(BaseBlockResource.class, recipeDeserializer);
		builder.registerTypeAdapter(BaseRecipe.class, recipeDeserializer);
		Gson gson = builder.create();

		for (String path : data.items) {
			try {
				ResourceLocation location = new ResourceLocation(BuilderMod.MODID + ":items/" + path + ".json");
				IResource resource = resourceManager.getResource(location);
				BaseItemResource itemResource = gson.fromJson(new InputStreamReader(resource.getInputStream()),
						BaseItemResource.class);
				Item item = ResourceConverter.toItem(itemResource);
				item.setUnlocalizedName(location.getResourceDomain() + "_" + path);
				GameRegistry.registerItem(item, path);
				itemModels.put(item, itemResource.model);
			} catch (IOException e) {
				// ignore
			}
		}
		for (String path : data.blocks) {
			try {
				ResourceLocation location = new ResourceLocation(BuilderMod.MODID + ":blocks/" + path + ".json");
				IResource resource = resourceManager.getResource(location);
				BaseBlockResource blockResource = gson.fromJson(new InputStreamReader(resource.getInputStream()),
						BaseBlockResource.class);
				Block block = ResourceConverter.toBlock(blockResource);
				block.setUnlocalizedName(location.getResourceDomain() + "_" + path);
				GameRegistry.registerBlock(block, path);
				blockModels.put(block, blockResource.model);
			} catch (IOException e) {
				// ignore
			}
		}
		for (String path : data.recipes) {
			try {
				ResourceLocation location = new ResourceLocation(BuilderMod.MODID + ":recipes/" + path + ".json");
				IResource resource = resourceManager.getResource(location);
				BaseRecipe recipe = gson.fromJson(new InputStreamReader(resource.getInputStream()), BaseRecipe.class);
				RecipeRegistry.register(recipe);
			} catch (IOException e) {
				// ignore
			}
		}
	}

	private void syncConfig() {
		if (config.hasChanged())
			config.save();
	}
}