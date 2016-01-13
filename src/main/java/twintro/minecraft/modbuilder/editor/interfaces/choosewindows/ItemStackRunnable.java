package twintro.minecraft.modbuilder.editor.interfaces.choosewindows;

import twintro.minecraft.modbuilder.data.resources.recipes.ItemStackResource;

public interface ItemStackRunnable {
	public void chooseItemStack(ItemStackResource item);
	public void itemStackChooserDispose();
}
