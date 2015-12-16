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
	TextureEditor editor;
	
	public TexturesActivityPanel(String header, String button) {
		super(header, button);
		editor = new TextureEditor(this);
	}

	@Override
	protected void add() {
		String name = JOptionPane.showInputDialog("Texture name:");
		if (name != null)
			if (name.replaceAll(" ", "").length() > 0)
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
		int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete " + value + ".png", 
				"Warning", JOptionPane.YES_NO_OPTION);
		if (result == JOptionPane.YES_OPTION){
			ResourcePackGenerator.deleteFile("assets/modbuilder/textures/" + value + ".png");
			removeElement(value);
		}
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
