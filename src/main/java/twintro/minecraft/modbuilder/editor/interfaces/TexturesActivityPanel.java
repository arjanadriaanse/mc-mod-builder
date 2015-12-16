package twintro.minecraft.modbuilder.editor.interfaces;

import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

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
		File folder = new File(ResourcePackGenerator.getTextureURL("assets/modbuilder/textures/"));
		if (folder.exists()){
			for (File file : folder.listFiles()){
				if (file.getAbsolutePath().endsWith(".png")){
					ImageIcon img = new ImageIcon(file.getAbsolutePath());
					String name = file.getName().substring(0, file.getName().length() - 4);
					addElement(name, img);
				}
			}
		}
	}
}
