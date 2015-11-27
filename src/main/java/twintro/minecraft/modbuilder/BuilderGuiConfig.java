package twintro.minecraft.modbuilder;

import java.util.List;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;

public class BuilderGuiConfig extends GuiConfig {

	public BuilderGuiConfig(GuiScreen parentScreen) {
		super(parentScreen, new ConfigElement(BuilderMod.getConfig().getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(), BuilderMod.MODID, false, false, GuiConfig.getAbridgedConfigPath(BuilderMod.getConfig().toString()));
	}
}
