package twintro.minecraft.modbuilder.editor.resources;

import javax.swing.ImageIcon;

import twintro.minecraft.modbuilder.data.resources.blocks.BaseBlockResource;
import twintro.minecraft.modbuilder.data.resources.models.BlockModelResources;
import twintro.minecraft.modbuilder.data.resources.models.BlockstatesResources;
import twintro.minecraft.modbuilder.data.resources.models.ItemModelResources;

public class BlockElement extends Element {
	public BaseBlockResource block;
	public String blockstateModel;
	public String blockModelTexture;
	public String itemModelParent;
	
	public Object getBlockstate(){
		return BlockstatesResources.regular(blockstateModel);
	}
	
	public Object getBlockModel(){
		return BlockModelResources.regular(blockModelTexture);
	}
	
	public Object getItemModel(){
		return ItemModelResources.blockItem(itemModelParent);
	}
}
