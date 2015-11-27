package twintro.minecraft.modbuilder;

import java.io.File;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.FileResourcePack;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = BuilderMod.MODID, version = BuilderMod.VERSION, guiFactory = "twintro.minecraft.modbuilder.BuilderModGuiFactory")
public class BuilderMod
{
    public static final String MODID = "modbuilder";
    public static final String VERSION = "0.1";
    
    private static Configuration config;
    private String[] resourceFiles;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
    	config = new Configuration(event.getSuggestedConfigurationFile());
    	syncConfig();
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	FMLCommonHandler.instance().bus().register(this);
    }
    
    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
		if(event.modID.equals(this.MODID))
			syncConfig();
    }

	private void syncConfig() {
		resourceFiles = config.getStringList("files", Configuration.CATEGORY_GENERAL, new String[0], "Mod Builder export files");
		reloadResources();
		
		if(config.hasChanged())
			config.save();
	}
	
	private void reloadResources() {
		SimpleReloadableResourceManager resourceManager = (SimpleReloadableResourceManager)Minecraft.getMinecraft().getResourceManager();
		for (String file : resourceFiles) {
			resourceManager.reloadResourcePack(new FileResourcePack(new File(file)));
		}
	}
	
	public static Configuration getConfig() {
		return config;
	}
}