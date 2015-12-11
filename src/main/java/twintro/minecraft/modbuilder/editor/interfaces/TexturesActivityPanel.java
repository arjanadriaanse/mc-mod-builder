package twintro.minecraft.modbuilder.editor.interfaces;

import java.awt.Image;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;

import twintro.minecraft.modbuilder.editor.ActivityPanel;
import twintro.minecraft.modbuilder.editor.generator.ResourcePackGenerator;

public class TexturesActivityPanel extends ActivityPanel {
	public TexturesActivityPanel(String header, String button) {
		super(header, button);
	}

	@Override
	protected void add() {
		//example of how to add an image to the files and list
		ImageIcon img = new ImageIcon(getClass().getResource(
				"/assets/examplemod/textures/items/jumpWand.png"));
		String name = "jumpWand";
		String loc = "assets/modbuilder/textures/items/";
		addImage(img, name, loc);
	}
	
	private void addImage(ImageIcon img, String name, String loc){
		ResourcePackGenerator.addTexture(toBufferedImage(img.getImage()), loc + name + ".png");
		addElement(name);
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
