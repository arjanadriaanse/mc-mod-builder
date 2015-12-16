package twintro.minecraft.modbuilder.editor.interfaces;

import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import twintro.minecraft.modbuilder.editor.ActivityPanel;
import twintro.minecraft.modbuilder.editor.generator.ResourcePackGenerator;

public class TexturesActivityPanel extends ActivityPanel {
	TexturesEditor editor;
	
	public TexturesActivityPanel(String header, String button) {
		super(header, button);
		editor = new TexturesEditor(this);
	}

	@Override
	protected void add() {
		//example of how to add an image to the files and list
		ImageIcon img = new ImageIcon(getClass().getResource(
				"/assets/examplemod/textures/items/jumpWand.png"));
		String name = "jumpWand";
		addImage(img, name);
	}
	
	public void addImage(ImageIcon img, String name){
		ResourcePackGenerator.addTexture(toBufferedImage(img.getImage()), "assets/modbuilder/textures/" + name + ".png");
		addElement(name, img);
	}
	
	@Override
	protected void edit() {
		String value = (String) list.getSelectedValue();
		editor.open(value, toBufferedImage(resizeImage(elements.get(value), 16, 16).getImage()));
	}
	
	@Override
	protected void delete() {
		String value = (String) list.getSelectedValue();
	}
	
	@Override
	public void updateList() {
		
	}
}
