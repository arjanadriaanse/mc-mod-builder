package twintro.minecraft.modbuilder.data.resources;

import net.minecraft.creativetab.CreativeTabs;

public enum TabResource {
	tabBlock(CreativeTabs.tabBlock), 
	tabDecorations(CreativeTabs.tabDecorations), 
	tabRedstone(CreativeTabs.tabRedstone), 
	tabTransport(CreativeTabs.tabTransport), 
	tabMisc(CreativeTabs.tabMisc), 
	tabAllSearch(CreativeTabs.tabAllSearch), 
	tabFood(CreativeTabs.tabFood), 
	tabTools(CreativeTabs.tabTools), 
	tabCombat(CreativeTabs.tabCombat), 
	tabBrewing(CreativeTabs.tabBrewing), 
	tabMaterials(CreativeTabs.tabMaterials), 
	tabInventory(CreativeTabs.tabInventory);
	
	private CreativeTabs value;
	
	private TabResource(CreativeTabs value){
		this.value = value;
	}
	
	public CreativeTabs getValue(){
		return value;
	}
}
