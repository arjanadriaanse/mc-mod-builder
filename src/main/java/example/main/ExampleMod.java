package example.main;

import example.blocks.MyBlocks;
import example.config.ConfigHandler;
import example.items.MyItems;
import example.proxies.ServerProxy;
import example.worldgen.WorldGeneration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = ModInformation.MODID, name = ModInformation.NAME, version = ModInformation.VERSION)
public class ExampleMod {
	@Instance(ModInformation.MODID)
	public static ExampleMod instance;
	
	@SidedProxy(clientSide = "example.proxies.ClientProxy", serverSide = "example.proxies.ServerProxy")
	public static ServerProxy proxy;
	
    @EventHandler
    //Use to initialize blocks, items, graphics, sounds, configs, etc.
    public void preInit(FMLPreInitializationEvent event){
    	MyItems.preInit();
    	MyBlocks.preInit();
    	WorldGeneration.init();
    	Recipes.init();
		ConfigHandler.init(event.getSuggestedConfigurationFile());
		proxy.init();
    }
    
    @EventHandler
    //Use to set things up for the mod
    public void init(FMLInitializationEvent event){
		MyItems.init(event);
		MyBlocks.init(event);
    }
    
    @EventHandler
    //Use to handle things afterwards such as doing things with other mods
    public void postInit(FMLPostInitializationEvent event){
    	
    }
}
