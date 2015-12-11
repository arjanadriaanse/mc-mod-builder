package twintro.minecraft.modbuilder.editor.interfaces;

import java.awt.Image;

import javax.swing.ImageIcon;

import twintro.minecraft.modbuilder.editor.ActivityPanel;
import twintro.minecraft.modbuilder.editor.generator.ResourcePackGenerator;

public class TexturesActivityPanel extends ActivityPanel {
	public TexturesActivityPanel(String header, String button) {
		super(header, button);
	}

	@Override
	protected void add() {
		String url = "assets/examplemod/textures/items/jumpWand.png";
		String dir = "assets/modbuilder/textures/items/jumpWand.png";
		ImageIcon img = new ImageIcon(getClass().getResource("/" + url));
		ResourcePackGenerator.addTexture(toBufferedImage(img.getImage()), dir);
		elements.put("jumpWand", getImage("jumpWand"));
		list.update(list.getGraphics());
	}
}
