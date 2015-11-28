package twintro.minecraft.modbuilder;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
import twintro.minecraft.modbuilder.data.BlockResource;
import twintro.minecraft.modbuilder.data.BuilderBlock;
import twintro.minecraft.modbuilder.data.BuilderItem;
import twintro.minecraft.modbuilder.data.ItemResource;
import twintro.minecraft.modbuilder.data.MetadataSection;
import twintro.minecraft.modbuilder.data.MetadataSerializer;
import twintro.minecraft.modbuilder.data.Resource;
import twintro.minecraft.modbuilder.data.ResourceHelper;
import twintro.minecraft.modbuilder.data.ResourceSerializer;

@Mod(modid = BuilderMod.MODID, version = BuilderMod.VERSION, guiFactory = "twintro.minecraft.modbuilder.BuilderModGuiFactory")
public class BuilderMod {
	public static final String MODID = "modbuilder";
	public static final String VERSION = "0.1";

	private static Configuration config;

	public static Configuration getConfig() {
		return config;
	}

	private Set<BlockResource> blocks = new HashSet<BlockResource>();
	private Set<ItemResource> items = new HashSet<ItemResource>();

	private ResourceSerializer serializer;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		ResourceHelper.init();
		serializer = new ResourceSerializer();

		importResources(Minecraft.getMinecraft().getResourceManager());

		config = new Configuration(event.getSuggestedConfigurationFile());
		syncConfig();
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		FMLCommonHandler.instance().bus().register(this);
		ItemModelMesher mesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();
		for (BlockResource resource : blocks) {
			mesher.register(Item.getItemFromBlock(resource.toBlock()), 0,
					new ModelResourceLocation(resource.model, "inventory"));
		}
		for (ItemResource resource : items) {
			mesher.register(resource.toItem(), 0, new ModelResourceLocation(resource.model, "inventory"));
		}
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
		Gson gson = new GsonBuilder().registerTypeHierarchyAdapter(Resource.class, serializer).setPrettyPrinting()
				.create();
		for (String path : data.blocks) {
			try {
				ResourceLocation location = new ResourceLocation("blocks/" + path + ".json");
				IResource resource = resourceManager.getResource(location);
				BlockResource blockResource = gson.fromJson(new InputStreamReader(resource.getInputStream()),
						BlockResource.class);
				BuilderBlock block = blockResource.toBlock();
				block.setUnlocalizedName(location.getResourceDomain() + "_" + path);
				GameRegistry.registerBlock(block, path);
				blocks.add(blockResource);
			} catch (IOException e) {
				// ignore
			}
		}
		for (String path : data.items) {
			try {
				ResourceLocation location = new ResourceLocation("items/" + path + ".json");
				IResource resource = resourceManager.getResource(location);
				ItemResource itemResource = gson.fromJson(new InputStreamReader(resource.getInputStream()),
						ItemResource.class);
				BuilderItem item = itemResource.toItem();
				item.setUnlocalizedName(location.getResourceDomain() + "_" + path);
				GameRegistry.registerItem(item, path);
				items.add(itemResource);
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