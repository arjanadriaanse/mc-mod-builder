package twintro.minecraft.modbuilder.editor.interfaces.helperclasses;

import twintro.minecraft.modbuilder.data.resources.models.BlockModelResource;

public interface BlockModelRunnable {
	public BlockModelResource getModel();
	public void setModel(BlockModelResource model);
	public void blockModelChooserDispose();
}
