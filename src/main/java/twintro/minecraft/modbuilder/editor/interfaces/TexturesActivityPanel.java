package twintro.minecraft.modbuilder.editor.interfaces;

import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import twintro.minecraft.modbuilder.editor.ActivityPanel;
import twintro.minecraft.modbuilder.editor.generator.ResourcePackGenerator;

public class TexturesActivityPanel extends ActivityPanel {
	TextureEditor editor;
	
	public TexturesActivityPanel(String header, String button) {
		super(header, button);
		editor = new TextureEditor(this);
	}

	@Override
	protected void add() {
		String name = JOptionPane.showInputDialog("Texture name:");
		editor.open(name, toBufferedImage(new ImageIcon().getImage(), 16, 16));
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
