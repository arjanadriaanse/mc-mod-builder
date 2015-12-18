package twintro.minecraft.modbuilder.editor.resources;

import twintro.minecraft.modbuilder.data.resources.blocks.BaseBlockResource;
import twintro.minecraft.modbuilder.data.resources.items.BaseItemResource;
import twintro.minecraft.modbuilder.data.resources.models.BlockModelResources;
import twintro.minecraft.modbuilder.data.resources.models.BlockstatesResources;
import twintro.minecraft.modbuilder.data.resources.models.ItemModelResources;

public class ItemElement extends Element {
	public BaseItemResource item;
	public String itemModelParent;
	
	public Object getItemModel(){
		return ItemModelResources.blockItem(itemModelParent);
	}
}
