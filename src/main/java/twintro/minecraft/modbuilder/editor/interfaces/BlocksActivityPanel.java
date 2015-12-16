package twintro.minecraft.modbuilder.editor.interfaces;

import java.io.IOException;

import javax.swing.ImageIcon;

import twintro.minecraft.modbuilder.editor.ActivityPanel;
import twintro.minecraft.modbuilder.editor.generator.ResourcePackGenerator;
import twintro.minecraft.modbuilder.editor.resources.BlockElement;

public class BlocksActivityPanel extends ActivityPanel {
	public BlocksActivityPanel(String header, String button) {
		super(header, button);
	}

	@Override
	protected void add() {
		
	}
	
	public void addBlock(BlockElement block){
		if (block.itemModelParent != null)
			createFile(block.getItemModel(), "assets/modbuilder/models/item/" + block.name + ".json");
		if (block.blockModelTexture != null)
			createFile(block.getBlockModel(), "assets/modbuilder/models/block/" + block.name + ".json");
		createFile(block.getBlockstate(), "assets/modbuilder/blockstates/" + block.name + ".json");
		createFile(block.block, "assets/modbuilder/blocks/" + block.name + ".json");
		addElement(block.name, block.image);
	}
	
	public void createFile(Object model, String dir){
		try {
			ResourcePackGenerator.createFile(model, dir);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void edit() {
		String value = (String) list.getSelectedValue();
	}
	
	@Override
	protected void delete() {
		String value = (String) list.getSelectedValue();
	}
	
	@Override
	public void updateList() {
		
	}
}
