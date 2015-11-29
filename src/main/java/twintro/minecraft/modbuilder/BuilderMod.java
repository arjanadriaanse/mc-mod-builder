package twintro.minecraft.modbuilder;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.ResourcePackRepository;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import twintro.minecraft.modbuilder.data.BuilderBlock;
import twintro.minecraft.modbuilder.data.BuilderItem;
import twintro.minecraft.modbuilder.data.MesherRegisterable;
import twintro.minecraft.modbuilder.data.MetadataSection;
import twintro.minecraft.modbuilder.data.MetadataSerializer;
import twintro.minecraft.modbuilder.data.ResourceDeserializer;
import twintro.minecraft.modbuilder.data.ResourceHelper;
import twintro.minecraft.modbuilder.data.resources.BlockResource;
import twintro.minecraft.modbuilder.data.resources.ItemResource;
import twintro.minecraft.modbuilder.data.resources.Recipe;

@Mod(modid = BuilderMod.MODID, version = BuilderMod.VERSION, guiFactory = "twintro.minecraft.modbuilder.BuilderModGuiFactory")
public class BuilderMod {
	public static final String MODID = "modbuilder";
	public static final String VERSION = "0.1";

	private static Configuration config;

	public static Configuration getConfig() {
		return config;
	}

	private Set<MesherRegisterable> models = new LinkedHashSet<MesherRegisterable>();

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
		for (MesherRegisterable model : models)
			model.register(mesher);
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
		builder.registerTypeAdapter(Recipe.class, recipeDeserializer);
		Gson gson = builder.create();

		for (String path : data.items) {
			try {
				ResourceLocation location = new ResourceLocation("items/" + path + ".json");
				IResource resource = resourceManager.getResource(location);
				ItemResource itemResource = gson.fromJson(new InputStreamReader(resource.getInputStream()),
						ItemResource.class);
				BuilderItem item = itemResource.toItem();
				item.setUnlocalizedName(location.getResourceDomain() + "_" + path);
				item.register(path);
				models.add(item);
			} catch (IOException e) {
				// ignore
			}
		}
		for (String path : data.blocks) {
			try {
				ResourceLocation location = new ResourceLocation("blocks/" + path + ".json");
				IResource resource = resourceManager.getResource(location);
				BlockResource blockResource = gson.fromJson(new InputStreamReader(resource.getInputStream()),
						BlockResource.class);
				BuilderBlock block = blockResource.toBlock();
				block.setUnlocalizedName(location.getResourceDomain() + "_" + path);
				block.register(path);
				models.add(block);
			} catch (IOException e) {
				// ignore
			}
		}
		for (String path : data.recipes) {
			try {
				ResourceLocation location = new ResourceLocation("recipes/" + path + ".json");
				IResource resource = resourceManager.getResource(location);
				Recipe recipe = gson.fromJson(new InputStreamReader(resource.getInputStream()), Recipe.class);
				recipe.register(path);
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